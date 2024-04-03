package com.srmanager.core.designsystem

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.srmanager.core.network.dto.Product
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar


fun generatePDF(
    context: Context,
    outletID: Int,
    orderDate: String,
    contact: String,
    productsList: List<Product>,
    total: Double
) {
    val pageHeight = 1120
    val pageWidth = 792
    val pdfDocument = PdfDocument()
    val paint = Paint()
    val title = Paint()
    val normalText = Paint()

    val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val myPage = pdfDocument.startPage(myPageInfo)

    val canvas: Canvas = myPage.canvas
    val bitmap: Bitmap? = drawableToBitmap(context.resources.getDrawable(R.drawable.app_icon))
    val scaleBitmap: Bitmap? = Bitmap.createScaledBitmap(bitmap!!, 60, 60, false)
    canvas.drawBitmap(scaleBitmap!!, 40f, 40f, paint)

    title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    title.textSize = 20f
    title.color = ContextCompat.getColor(context, R.color.black)

    normalText.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    normalText.textSize = 15f
    normalText.color = ContextCompat.getColor(context, R.color.black)

    canvas.drawText("SR Manager", 400f, 60f, title)
    canvas.drawText("Make it Easy", 400f, 80f, normalText)

    paint.setColor(ContextCompat.getColor(context, R.color.black)) // Set line color
    paint.strokeWidth = 2f // Set line width
    canvas.drawLine(40f, 120f, pageWidth.toFloat() - 40f, 120f, normalText)

    canvas.drawText("Outlet ID: $outletID", 40f, 140f, normalText)
    canvas.drawText("Contact: $contact", 40f, 160f, normalText)
    canvas.drawText("Order Date : $orderDate", 40f, 180f, normalText)


    val startX = 40f
    val startY = 200f
    val rowHeight = 20f
    val columnWidth = 150f

// Header
    canvas.drawText("Product ID", startX, startY, normalText)
    canvas.drawText("Price", startX + columnWidth, startY, normalText)
    canvas.drawText("MRP Price", startX + columnWidth * 2, startY, normalText)
    canvas.drawText("Total", startX + columnWidth * 3, startY, normalText)

// Draw product list
    var yPosition = startY
    productsList.forEachIndexed { index, product ->
        yPosition += rowHeight
        canvas.drawText(product.id.toString(), startX, yPosition + 5f, normalText)
        canvas.drawText(product.price.toString(), startX + columnWidth, yPosition + 5f, normalText)
        canvas.drawText(
            product.mrpPrice.toString(),
            startX + columnWidth * 2,
            yPosition + 5f,
            normalText
        )
        canvas.drawText(
            product.selectedItemTotalPrice.toString(),
            startX + columnWidth * 3,
            yPosition + 5f,
            normalText
        )

        // Draw a line below each row (optional)
        if (index < productsList.size - 1) {
            canvas.drawLine(
                startX,
                yPosition + 10f,
                pageWidth.toFloat() - startX,
                yPosition + 10f,
                paint
            )
        }
    }

// Draw a line after the header
    canvas.drawLine(startX, startY + 10f, pageWidth.toFloat() - startX, startY + 10f, paint)

// Draw total at the end
    yPosition += rowHeight
    canvas.drawText("Total: $total", startX, yPosition + 20f, normalText)

    pdfDocument.finishPage(myPage)

    val dir = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
        "SRManager"
    )

    if (!dir.exists()) {
        dir.mkdir()
    }

    val file = File(dir, generateFileName("srm") + ".pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(
            context,
            "PDF file generated successfully at ${file.path.toString()}",
            Toast.LENGTH_SHORT
        ).show()
    } catch (ex: Exception) {
        Log.d("dataxx", ex.message.toString())
    }
    pdfDocument.close()

    // Open the PDF
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    context.startActivity(intent)
}

fun drawableToBitmap(drawable: Drawable): Bitmap? {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

fun generateFileName(fileName: String): String {
    return fileName + SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().time)
}

fun renderComposableToBitmap(activity: Activity, content: @Composable () -> Unit): Bitmap {
    // Create a ComposeView and set its content to the Composable function
    val composeView = ComposeView(activity).apply {
        setContent {
            content()
        }
    }

    // Measure and layout the view
    composeView.measure(
        View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(1920, View.MeasureSpec.EXACTLY)
    )
    composeView.layout(0, 0, composeView.measuredWidth, composeView.measuredHeight)

    // Capture the ComposeView as a Bitmap
    return captureComposeView(composeView)
}

fun captureComposeView(composeView: ComposeView): Bitmap {
    val bitmap = Bitmap.createBitmap(composeView.width, composeView.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    composeView.draw(canvas)
    return bitmap
}

fun saveBitmapAsPDF(bitmap: Bitmap, context: Context) {
    val pdfDocument = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    val canvas = page.canvas
    canvas.drawBitmap(bitmap, 0f, 0f, null)
    pdfDocument.finishPage(page)

    val dir = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
        "SRManager"
    )

    if (!dir.exists()) {
        dir.mkdir()
    }

    val file = File(dir, generateFileName("srm") + ".pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(
            context,
            "PDF file generated successfully at ${file.path.toString()}",
            Toast.LENGTH_SHORT
        ).show()
    } catch (ex: Exception) {
        Log.d("dataxx", ex.message.toString())
    }
    pdfDocument.close()

    // Open the PDF
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    context.startActivity(intent)
}

fun getActivity(context: Context?): Activity? {
    var currentContext = context
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}
