package com.srmanager.core.designsystem

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.srmanager.core.common.util.base64ToImage
import com.srmanager.order_domain.model.OrderDetailsResponse
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar

const val pageHeight = 1120
const val pageWidth = 792
private const val rowHeight = 30f // Adjust row height as needed
private val columnWidths = listOf(
    (pageWidth / 12).toFloat(),
    ((pageWidth / 12) * 3).toFloat() - 20f,
    (pageWidth / 12).toFloat(),
    (pageWidth / 12).toFloat(),
    (pageWidth / 12).toFloat(),
    (pageWidth / 12).toFloat(),
    (pageWidth / 12).toFloat(),
    (pageWidth / 12).toFloat(),
    (pageWidth / 12).toFloat()
)


@SuppressLint("UseCompatLoadingForDrawables")
fun generatePDF(
    context: Context, orderDetails: OrderDetailsResponse
) {
    val headerBoldTextPaint = TextPaint().apply {
        textSize = 20f // Adjust text size as needed
        color = Color.BLACK // Adjust text color as needed
        isFakeBoldText = true // Make the header text bold
        textAlign = Paint.Align.RIGHT
    }

    val headerNormalTextPaint = TextPaint().apply {
        textSize = 12f // Adjust text size as needed
        color = Color.BLACK // Adjust text color as needed
        textAlign = Paint.Align.RIGHT
    }

    val normalTextPaint = TextPaint().apply {
        textSize = 10f // Adjust text size as needed
        color = Color.BLACK // Adjust text color as needed
    }

    val boldTextPaint = TextPaint().apply {
        textSize = 10f // Adjust text size as needed
        color = Color.BLACK // Adjust text color as needed
        isFakeBoldText = true // Make the header text bold
    }

    val salesOrderTextPaint = TextPaint().apply {
        textSize = 16f // Adjust text size as needed
        color = Color.BLACK // Adjust text color as needed
        isFakeBoldText = true // Make the header text bold
        textAlign = Paint.Align.CENTER
    }

    val startYAxisPointAfterTop = 200f

    val pdfDocument = PdfDocument()
    val paint = Paint()
    val title = Paint()
    val normalText = Paint()

    val columnWidthForSalesMan = listOf(
        (pageWidth / 5).toFloat(),
        (pageWidth / 5).toFloat(),
        (pageWidth / 5).toFloat(),
        (pageWidth / 5).toFloat(),
        (pageWidth / 5).toFloat(),
    )

    val columnWidthForCustomer = listOf(
        (pageWidth / 3).toFloat(),
        (pageWidth / 3).toFloat(),
        (pageWidth / 3).toFloat()
    )

    val myPageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    val myPage = pdfDocument.startPage(myPageInfo)

    val canvas: Canvas = myPage.canvas

    title.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    title.textSize = 12f
    title.color = ContextCompat.getColor(context, R.color.black)

    normalText.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    normalText.textSize = 12f
    normalText.color = ContextCompat.getColor(context, R.color.black)

    paint.setColor(ContextCompat.getColor(context, R.color.black)) // Set line color
    paint.strokeWidth = 2f // Set line width

    val bitmap: Bitmap? =
        drawableToBitmap(context.resources.getDrawable(R.drawable.ic_header_image))

    canvas.drawBitmap(
        bitmap!!, null, android.graphics.RectF(40f, 20f, 150f, 150f), paint
    )


    canvas.drawText(
        "Latino Marketing (M) SDN BHD".uppercase(),
        pageWidth.toFloat() - 40f,
        60f,
        headerBoldTextPaint
    )
    canvas.drawText(
        "Reg. No- (1484541-T), Bandar Sunway 47500,",
        pageWidth.toFloat() - 40f,
        60f + 20f,
        headerNormalTextPaint
    )
    canvas.drawText(
        "Subang Jaya, Selangor, Malaysia. Tel: +601131464097",
        pageWidth.toFloat() - 40f,
        60f + (20f * 2),
        headerNormalTextPaint
    )
    canvas.drawText(
        "Email:info@latino.com.my Web: www.latino.com.my",
        pageWidth.toFloat() - 40f,
        60f + (20f * 3),
        headerNormalTextPaint
    )

    val salesOrderText = "Sales Order"

    val textBounds = Rect()
    val x = (pageWidth - 40f - textBounds.width()) / 2f - textBounds.left

    boldTextPaint.getTextBounds(salesOrderText, 0, salesOrderText.length, textBounds)

    canvas.drawText(salesOrderText, x, startYAxisPointAfterTop - 40f, salesOrderTextPaint)

    canvas.drawText("Order No: ", 40f, startYAxisPointAfterTop, boldTextPaint)

    canvas.drawText(orderDetails.orderNo, 130f, startYAxisPointAfterTop, normalTextPaint)

    canvas.drawText("Order Date: ", 40f + pageWidth / 2, startYAxisPointAfterTop, boldTextPaint)

    canvas.drawText(
        orderDetails.orderDate, 130f + pageWidth / 2, startYAxisPointAfterTop, normalTextPaint
    )

    canvas.drawText("Customer Details", 40f, startYAxisPointAfterTop + 30f, boldTextPaint)

    canvas.apply {
        val startPosition = 60f
        val startX = 40f
        val startY = startYAxisPointAfterTop + startPosition

        val columnStartPoint = startPosition - 18f
        val columnEndPoint = columnStartPoint + 58f

        canvas.drawLine(
            startX,
            startYAxisPointAfterTop + columnStartPoint,
            pageWidth.toFloat() - startX,
            startYAxisPointAfterTop + columnStartPoint,
            normalText
        )

        // Draw header
        drawTableRow(
            startX + 2f, startY, boldTextPaint, listOf(
                "CUSTOMER CODE", "CUSTOMER NAME", "CUSTOMER MOBILE"
            ), false, columnWidthForCustomer
        )

        drawTableRow(
            startX + 2f, startY + rowHeight, normalText, listOf(
                orderDetails.customerCode, orderDetails.customerName, orderDetails.contactNo
            ), false, columnWidthForCustomer
        )

        drawLine(startX, startY + 10f, pageWidth - startX, startY + 10f, normalText)

        drawLine(
            startX,
            startYAxisPointAfterTop + columnStartPoint,
            startX,
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawLine(
            startX + columnWidthForCustomer[0],
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidthForCustomer[0],
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawLine(
            startX + columnWidthForCustomer.slice(0 until 1).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidthForCustomer.slice(0 until 1).sum(),
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawLine(
            startX + columnWidthForCustomer.slice(0 until 2).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidthForCustomer.slice(0 until 2).sum(),
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawLine(
            pageWidth - startX,
            startYAxisPointAfterTop + columnStartPoint,
            pageWidth - startX,
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawLine(
            startX,
            startY + rowHeight + 10f,
            pageWidth - startX,
            startY + rowHeight + 10f,
            normalText
        )
    }

    canvas.apply {

        val startPosition = 120f
        val startX = 40f
        val startY = startYAxisPointAfterTop + startPosition

        val columnStartPoint = startPosition - 18f
        val columnEndPoint = columnStartPoint + 58f

        canvas.drawText(
            "SHIP TO",
            startX + 2f,
            startYAxisPointAfterTop + startPosition,
            boldTextPaint
        )

        val staticLayout = StaticLayout(
            orderDetails.outletAddress,
            normalTextPaint,
            (pageWidth / 8) * 2,
            Layout.Alignment.ALIGN_NORMAL,
            1.0f,
            0.0f,
            false
        )

        canvas.save()
        canvas.translate(
            startX + (pageWidth / 8) + 2f, startY - 10f
        )
        staticLayout.draw(canvas)
        canvas.restore()

        canvas.drawText(
            "BILL TO",
            (pageWidth.toFloat() / 2) + 2f,
            startY,
            boldTextPaint
        )

        val billStaticLayout = StaticLayout(
            orderDetails.billingAddress,
            normalTextPaint,
            (pageWidth / 8) * 2,
            Layout.Alignment.ALIGN_NORMAL,
            1.0f,
            0.0f,
            false
        )

        canvas.save()
        canvas.translate(
            (pageWidth.toFloat() / 2) + (pageWidth / 8) + 2f,
            startY - 10f
        ) // Move canvas to the desired position
        billStaticLayout.draw(canvas)
        canvas.restore()


        //horizontal lines start
        canvas.drawLine(
            startX,
            startYAxisPointAfterTop + columnStartPoint,
            startX,
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        ) // column 1

        canvas.drawLine(
            (pageWidth.toFloat() / 2) + (pageWidth / 8),
            startYAxisPointAfterTop + columnStartPoint,
            (pageWidth.toFloat() / 2) + (pageWidth / 8),
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        canvas.drawLine(
            startX + (pageWidth / 8),
            startYAxisPointAfterTop + columnStartPoint,
            startX + (pageWidth / 8),
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )


        canvas.drawLine(
            pageWidth.toFloat() / 2,
            startYAxisPointAfterTop + columnStartPoint,
            pageWidth.toFloat() / 2,
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        canvas.drawLine(
            pageWidth.toFloat() - startX,
            startYAxisPointAfterTop + columnStartPoint,
            pageWidth.toFloat() - startX,
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )
        //horizontal lines end

        //vertical lines start
        canvas.drawLine(
            startX,
            startYAxisPointAfterTop + columnStartPoint,
            pageWidth.toFloat() - startX,
            startYAxisPointAfterTop + columnStartPoint,
            normalText
        )

        canvas.drawLine(
            startX,
            startYAxisPointAfterTop + columnEndPoint,
            pageWidth.toFloat() - startX,
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )
        //vertical lines end
    }

    canvas.apply {
        val startPosition = 190f
        val startX = 40f
        val startY = startYAxisPointAfterTop + startPosition

        val columnStartPoint = startPosition - 18f
        val columnEndPoint = columnStartPoint + 58f

        canvas.drawLine(
            startX,
            startYAxisPointAfterTop + columnStartPoint,
            pageWidth.toFloat() - startX,
            startYAxisPointAfterTop + columnStartPoint,
            normalText
        )

        // Draw header
        drawTableRow(
            startX + 2f, startY, boldTextPaint, listOf(
                "SALESMAN NAME", "SALESMAN MOBILE", "PAYMENT", "MARKET", "ROUTE"
            ), false, columnWidthForSalesMan
        )

        drawLine(startX, startY + 10f, pageWidth - startX, startY + 10f, normalText)

        drawLine(
            startX,
            startYAxisPointAfterTop + columnStartPoint,
            startX,
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawLine(
            startX + columnWidthForSalesMan[0],
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidthForSalesMan[0],
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawLine(
            40f + columnWidthForSalesMan.slice(0 until 1).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            40f + columnWidthForSalesMan.slice(0 until 1).sum(),
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawLine(
            startX + columnWidthForSalesMan.slice(0 until 2).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidthForSalesMan.slice(0 until 2).sum(),
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawLine(
            startX + columnWidthForSalesMan.slice(0 until 3).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidthForSalesMan.slice(0 until 3).sum(),
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawLine(
            startX + columnWidthForSalesMan.slice(0 until 4).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidthForSalesMan.slice(0 until 4).sum(),
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawLine(
            pageWidth - startX,
            startYAxisPointAfterTop + columnStartPoint,
            pageWidth - startX,
            startYAxisPointAfterTop + columnEndPoint,
            normalText
        )

        drawTableRow(
            startX + 2f, startY + rowHeight, normalText, listOf(
                orderDetails.salesMan,
                orderDetails.salesManMobile,
                orderDetails.paymentType,
                orderDetails.market,
                orderDetails.route
            ), false, columnWidthForSalesMan
        )

        drawLine(
            startX,
            startY + rowHeight + 10f,
            pageWidth - startX,
            startY + rowHeight + 10f,
            normalText
        )
    }

    canvas.apply {
        val startPosition = 260f
        val startX = 40f
        var startY = startPosition + startYAxisPointAfterTop
        val columnStartPoint = startPosition - 18f

        drawLine(startX, startY - 20f, pageWidth - startX, startY - 20f, normalText)

        // Draw header
        drawTableRow(
            startX, startY, boldTextPaint, listOf(
                "Code", "Title", "Qty", "MRP", "Dis. %", "Dis. Amt", "After Dis.", "GST", "Net Amt"
            ), true, columnWidths
        )

        // Draw a line after header
        drawLine(startX, startY + 10f, pageWidth - startX, startY + 10f, normalText)

        // Draw product list
        orderDetails.data.forEach { orderItem ->
            startY += rowHeight
            drawTableRow(
                startX, startY, normalTextPaint, listOf(
                    orderItem.productCode,
                    TextUtils.ellipsize(
                        orderItem.productName,
                        normalTextPaint,
                        columnWidths[1] - 5f,
                        TextUtils.TruncateAt.END
                    ).toString(),
                    orderItem.quantity.toString(),
                    orderItem.mrp.toString(),
                    orderItem.discountPercentage.toString(),
                    orderItem.discountAmount.toString(),
                    orderItem.afterDiscount.toString(),
                    orderItem.vatAmount.toString(),
                    orderItem.netAmount.toString()
                ), isDrawLine = true, columnWidths
            )
        }

        // Draw total at the end
        startY += rowHeight
        drawTableRow(
            startX, startY, boldTextPaint, listOf(
                orderDetails.inWords,
                "",
                "",
                "",
                "",
                "Total",
                "",
                "",
                String.format("%.2f", orderDetails.data.sumOf {
                    it.netAmount
                })
            ), isDrawLine = false, columnWidths
        )

        drawLine(
            startX,
            startYAxisPointAfterTop + columnStartPoint,
            startX,
            startY + 5f,
            normalText
        )

        drawLine(
            startX + columnWidths[0],
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidths[0],
            startY + 5f - rowHeight,
            normalText
        )
        drawLine(
            startX + columnWidths.slice(0 until 1).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidths.slice(0 until 1).sum(),
            startY + 5f - rowHeight,
            normalText
        )
        drawLine(
            startX + columnWidths.slice(0 until 2).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidths.slice(0 until 2).sum(),
            startY + 5f - rowHeight,
            normalText
        )
        drawLine(
            startX + columnWidths.slice(0 until 3).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidths.slice(0 until 3).sum(),
            startY + 5f - rowHeight,
            normalText
        )

        drawLine(
            startX + columnWidths.slice(0 until 4).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidths.slice(0 until 4).sum(),
            startY + 5f - rowHeight,
            normalText
        )

        drawLine(
            startX + columnWidths.slice(0 until 5).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidths.slice(0 until 5).sum(),
            startY + 5f,
            normalText
        )

        drawLine(
            startX + columnWidths.slice(0 until 6).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidths.slice(0 until 6).sum(),
            startY + 5f,
            normalText
        )

        drawLine(
            startX + columnWidths.slice(0 until 7).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidths.slice(0 until 7).sum(),
            startY + 5f,
            normalText
        )

        drawLine(
            startX + columnWidths.slice(0 until 8).sum(),
            startYAxisPointAfterTop + columnStartPoint,
            startX + columnWidths.slice(0 until 8).sum(),
            startY + 5f,
            normalText
        )
        drawLine(
            pageWidth - 40f,
            startYAxisPointAfterTop + columnStartPoint,
            pageWidth - 40f,
            startY + 5f,
            normalText
        )


        canvas.drawText(
            "Signature", startX + columnWidths.slice(0 until 8).sum(), startY + 120f, normalTextPaint
        )

        canvas.translate(
            35f + columnWidths.slice(0 until 8).sum(), startY + 20f
        )
        try {
            if (orderDetails.signature.isNotEmpty()) {
                canvas.drawBitmap(
                    base64ToImage(orderDetails.signature),
                    null,
                    android.graphics.RectF(0f, 0f, 100f, 100f),
                    paint
                )
            }
        } catch (_: Exception) {

        }


    }

    pdfDocument.finishPage(myPage)

    val dir = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "SRManager"
    )

    if (!dir.exists()) {
        dir.mkdir()
    }

    val file = File(dir, generateFileName("srm") + ".pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        Toast.makeText(
            context, "PDF file generated successfully at ${file.path}", Toast.LENGTH_SHORT
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

@SuppressLint("SimpleDateFormat")
fun generateFileName(fileName: String): String {
    return fileName + SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().time)
}


private fun Canvas.drawTableRow(
    startX: Float,
    yPosition: Float,
    paint: Paint,
    rowData: List<String>,
    isDrawLine: Boolean,
    columnWidths: List<Float>
) {
    var x = startX
    rowData.forEachIndexed { index, text ->
        drawText(text, x + 2f, yPosition, paint)
        x += columnWidths[index]
    }

    // Draw horizontal lines
    if (isDrawLine) {
        drawLine(
            startX, yPosition + rowHeight + 5f, pageWidth - 40f, yPosition + rowHeight + 5f, paint
        )
    }
}

fun drawableToBitmap(drawable: Drawable): Bitmap? {
    if (drawable is BitmapDrawable) {
        return drawable.bitmap
    }
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}