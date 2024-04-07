package com.srmanager.core.designsystem

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.text.TextPaint
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.srmanager.core.network.dto.Product
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar

private const val rowHeight = 30f // Adjust row height as needed
private val columnWidths = listOf(100f, 200f, 100f, 100f, 100f, 100f)

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
    /*val bitmap: Bitmap? = drawableToBitmap(context.resources.getDrawable(R.drawable.app_icon))
    val scaleBitmap: Bitmap? = Bitmap.createScaledBitmap(bitmap!!, 60, 60, false)
    canvas.drawBitmap(scaleBitmap!!, 40f, 40f, paint)*/

    title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    title.textSize = 20f
    title.color = ContextCompat.getColor(context, R.color.black)

    normalText.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    normalText.textSize = 15f
    normalText.color = ContextCompat.getColor(context, R.color.black)

 /*   canvas.drawText("SR Manager", 400f, 60f, title)
    canvas.drawText("Make it Easy", 400f, 80f, normalText)
*/
    paint.setColor(ContextCompat.getColor(context, R.color.black)) // Set line color
    paint.strokeWidth = 2f // Set line width
    canvas.drawLine(40f, 120f, pageWidth.toFloat() - 40f, 120f, normalText)

    canvas.drawText("Outlet ID: $outletID", 42f, 140f, normalText)
    canvas.drawText("Contact: $contact", 42f, 160f, normalText)
    canvas.drawText("Order Date : $orderDate", 42f, 180f, normalText)

    canvas.drawLine(40f, 190f, pageWidth.toFloat() - 40f, 190f, normalText)

    canvas.drawLine(40f, 192f, pageWidth.toFloat() - 40f, 192f, normalText)

    canvas.drawLine(40f, 120f, 40f, 190f, normalText)

    canvas.drawLine(pageWidth.toFloat() - 40f, 120f, pageWidth.toFloat() - 40f, 190f, normalText)

    canvas.drawLine(pageWidth.toFloat()/2, 120f, pageWidth.toFloat() /2, 190f, normalText)
    /*val startX = 40f
    val startY = 250f
    val rowHeight = 20f
    val columnWidth = 150f

// Header
    canvas.drawText("Product ID", startX, startY, normalText)
    canvas.drawText("Title", startX + 100f, startY, normalText)
    canvas.drawText("Quantity", startX + columnWidth * 2, startY, normalText)
    canvas.drawText("Price", startX + columnWidth * 3, startY, normalText)
    canvas.drawText("MRP Price", startX + columnWidth * 4, startY, normalText)
    canvas.drawText("Total", startX + columnWidth * 5, startY, normalText)

    val textPaint = TextPaint()
// Draw product list
    var yPosition = startY
    productsList.forEachIndexed { index, product ->
        yPosition += rowHeight
        canvas.drawText(product.id.toString(), startX, yPosition + 5f, normalText)
        canvas.drawText(
            TextUtils.ellipsize(product.title, textPaint, startX + 100f, TextUtils.TruncateAt.END).toString(),
            startX + 100f,
            yPosition + 5f,
            normalText
        )
        canvas.drawText(
            product.availableQuantity.toString(),
            startX + columnWidth*2,
            yPosition + 5f,
            normalText
        )
        canvas.drawText(
            product.price.toString(),
            startX + columnWidth * 3,
            yPosition + 5f,
            normalText
        )
        canvas.drawText(
            product.mrpPrice.toString(),
            startX + columnWidth * 4,
            yPosition + 5f,
            normalText
        )
        canvas.drawText(
            product.selectedItemTotalPrice.toString(),
            startX + columnWidth * 5,
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
    canvas.drawText("Total", startX, yPosition + 20f, normalText)

    canvas.drawText("$total", startX + columnWidth * 4, yPosition + 20f, normalText)*/

    val normalTextPaint = TextPaint().apply {
        textSize = 16f // Adjust text size as needed
        color = Color.BLACK // Adjust text color as needed
    }

    val headerTextPaint = TextPaint().apply {
        textSize = 16f // Adjust text size as needed
        color = Color.BLACK // Adjust text color as needed
        isFakeBoldText = true // Make the header text bold
    }

    canvas.apply {
        val startX = 40f
        var startY = 250f

        // Draw header
        drawTableRow(
            startX, startY, headerTextPaint, listOf(
                "Product ID", "Title", "Quantity", "Price", "MRP Price", "Total"
            ), true
        )

        // Draw a line after header
        drawLine(startX, startY + 10f, startX + columnWidths.sum(), startY + 10f, paint)

        // Draw product list
        productsList.forEach { product ->
            startY += rowHeight
            drawTableRow(
                startX, startY, normalTextPaint, listOf(
                    product.id.toString(),
                    TextUtils.ellipsize(
                        product.title,
                        normalTextPaint,
                        columnWidths[1] - 20f,
                        TextUtils.TruncateAt.END
                    ).toString(),
                    product.availableQuantity.toString(),
                    product.price.toString(),
                    product.mrpPrice.toString(),
                    product.selectedItemTotalPrice.toString()
                ), isDrawLine = true
            )
        }

        // Draw total at the end
        startY += rowHeight
        drawTableRow(
            startX, startY, normalTextPaint, listOf(
                "Total", "", "", "", "", String.format("%.2f", total)
            ), isDrawLine = false
        )
    }

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


private fun Canvas.drawTableRow(
    startX: Float,
    yPosition: Float,
    paint: Paint,
    rowData: List<String>,
    isDrawLine: Boolean
) {
    var x = startX
    rowData.forEachIndexed { index, text ->
        drawText(text, x, yPosition, paint)
        x += columnWidths[index]
    }

    // Draw horizontal lines
    if (isDrawLine){
        drawLine(
            startX,
            yPosition + rowHeight + 5f,
            startX + columnWidths.sum(),
            yPosition + rowHeight + 5f,
            paint
        )
    }
}