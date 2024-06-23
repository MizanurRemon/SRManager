package com.srmanager.core.designsystem

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Div
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.HorizontalAlignment
import com.itextpdf.layout.property.TextAlignment
import com.srmanager.core.common.util.base64ToImage
import com.srmanager.order_domain.model.OrderDetailsResponse
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

fun generatePdf(context: Context, orderDetails: OrderDetailsResponse) {
    val dir = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "SRManager"
    )

    if (!dir.exists()) {
        dir.mkdir()
    }
//generateFileName("srm")
    val file = File(dir, "srm${orderDetails.orderNo}" + ".pdf")

    if (!file.exists()) {
        val pdfWriter = PdfWriter(file)
        val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(pdfWriter)
        val document = Document(pdfDocument)

        // Custom Fonts
        val regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA)
        val boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)

        val normalFontSize = 10f

        try {

            val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_header_image)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imageData = ImageDataFactory.create(stream.toByteArray())
            val image = Image(imageData)
            image.setHeight(80f)
            image.setWidth(80f)
            image.setHorizontalAlignment(HorizontalAlignment.CENTER)

            val headerTable = Table(
                floatArrayOf(
                    1f,
                    1f
                )
            ).useAllAvailableWidth()

            val infoTable = Table(
                floatArrayOf(
                    1f
                )
            ).setTextAlignment(TextAlignment.RIGHT).useAllAvailableWidth()


            infoTable.addCell(
                Cell().add(
                    Paragraph("Latino Marketing (M) SDN BHD").setFontSize(16f).setFont(boldFont)

                ).setBorder(Border.NO_BORDER)
            )
            infoTable.addCell(
                Cell().add(
                    Paragraph("Reg. No- (1484541-T), Bandar Sunway 47500,").setFontSize(
                        normalFontSize
                    )
                ).setBorder(Border.NO_BORDER)
            )

            infoTable.addCell(
                Cell().add(
                    Paragraph("Subang Jaya, Selangor, Malaysia. Tel: +601131464097").setFontSize(
                        normalFontSize
                    )
                ).setBorder(Border.NO_BORDER)
            )

            infoTable.addCell(
                Cell().add(
                    Paragraph("Email:info@latino.com.my Web: www.latino.com.my").setFontSize(
                        normalFontSize
                    )
                ).setBorder(Border.NO_BORDER)
            )

            headerTable.addCell(
                Cell().add(image.setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER)
            )
            headerTable.addCell(Cell().add(infoTable).setBorder(Border.NO_BORDER))

            document.add(headerTable)

            document.add(Div().setHeight(10f))

            document.add(
                Paragraph("Sales Order")
                    .setFont(boldFont)
                    .setFontSize(12f)
                    .setTextAlignment(TextAlignment.CENTER)
            )
            document.add(Div().setHeight(5f))

            val orderTable = Table(
                floatArrayOf(
                    1f,
                    1f
                )
            ).useAllAvailableWidth()

            val orderNoTable = Table(
                2
            ).setFontSize(normalFontSize)

            orderNoTable.addCell(
                Cell().add(Paragraph("Order No:").setFont(boldFont)).setBorder(Border.NO_BORDER)
            )
            orderNoTable.addCell(
                Cell().add(Paragraph(orderDetails.orderNo)).setBorder(Border.NO_BORDER)
            )

            val orderDateTable = Table(
                2
            ).setFontSize(normalFontSize)

            orderDateTable.addCell(
                Cell().add(Paragraph("Order Date:").setFont(boldFont)).setBorder(Border.NO_BORDER)
            )
            orderDateTable.addCell(
                Cell().add(Paragraph(orderDetails.orderDate)).setBorder(Border.NO_BORDER)
            )

            orderTable.addCell(
                Cell().add(orderNoTable).setTextAlignment(TextAlignment.LEFT).setBorder(
                    Border.NO_BORDER
                )
            )

            orderTable.addCell(
                Cell().add(orderDateTable).setTextAlignment(TextAlignment.RIGHT).setBorder(
                    Border.NO_BORDER
                )
            )

            document.add(orderTable)

            document.add(Div().setHeight(5f))

            document.add(
                Paragraph("Customer Details").setFont(boldFont).setFontSize(normalFontSize)
                    .setTextAlignment(TextAlignment.LEFT)
            )
            document.add(Div().setHeight(2f))

            val customerTable = Table(
                floatArrayOf(
                    1f,
                    1f,
                    1f
                )
            ).useAllAvailableWidth().setFontSize(normalFontSize)
            customerTable.addCell(Cell().add(Paragraph("CUSTOMER CODE").setFont(boldFont)))
            customerTable.addCell(Cell().add(Paragraph("CUSTOMER NAME").setFont(boldFont)))
            customerTable.addCell(Cell().add(Paragraph("CUSTOMER MOBILE").setFont(boldFont)))

            customerTable.addCell(Cell().add(Paragraph(orderDetails.customerCode)))
            customerTable.addCell(Cell().add(Paragraph(orderDetails.customerName)))
            customerTable.addCell(Cell().add(Paragraph(orderDetails.contactNo)))

            document.add(customerTable)

            document.add(Div().setHeight(2f))


            val billingTable = Table(
                floatArrayOf(
                    1f,
                    1f,
                    1f,
                    1f
                )
            ).useAllAvailableWidth().setFontSize(normalFontSize)
            billingTable.addCell(Cell().add(Paragraph("SHIP TO").setFont(boldFont)))
            billingTable.addCell(Cell().add(Paragraph(orderDetails.outletAddress)))
            billingTable.addCell(Cell().add(Paragraph("BILL TO").setFont(boldFont)))
            billingTable.addCell(Cell().add(Paragraph(orderDetails.billingAddress)))

            document.add(billingTable)

            document.add(Div().setHeight(5f))

            val salesManTable = Table(
                floatArrayOf(
                    1f,
                    1f,
                    1f,
                    1f,
                    1f
                )
            ).useAllAvailableWidth().setFontSize(normalFontSize)
            salesManTable.addCell(Cell().add(Paragraph("SALESMAN MAN").setFont(boldFont)))
            salesManTable.addCell(Cell().add(Paragraph("SALESMAN MOBILE").setFont(boldFont)))
            salesManTable.addCell(Cell().add(Paragraph("PAYMENT").setFont(boldFont)))
            salesManTable.addCell(Cell().add(Paragraph("MARKET").setFont(boldFont)))
            salesManTable.addCell(Cell().add(Paragraph("ROUTE").setFont(boldFont)))

            salesManTable.addCell(Cell().add(Paragraph(orderDetails.salesMan)))
            salesManTable.addCell(Cell().add(Paragraph(orderDetails.salesManMobile)))
            salesManTable.addCell(Cell().add(Paragraph(orderDetails.paymentType)))
            salesManTable.addCell(Cell().add(Paragraph(orderDetails.market)))
            salesManTable.addCell(Cell().add(Paragraph(orderDetails.route)))

            document.add(salesManTable)

            document.add(Div().setHeight(5f))
            // Add a Table
            val productTable = Table(
                floatArrayOf(
                    1f,
                    1f,
                    1f,
                    1f,
                    1f,
                    1f,
                    1f,
                    1f,
                    1f
                )
            ).useAllAvailableWidth().setFontSize(normalFontSize)// 9 columns
            productTable.addCell(Cell().add(Paragraph("Code").setFont(boldFont)))
            productTable.addCell(Cell().add(Paragraph("Title").setFont(boldFont)))
            productTable.addCell(Cell().add(Paragraph("Qty").setFont(boldFont)))
            productTable.addCell(Cell().add(Paragraph("Unit Price").setFont(boldFont)))
            productTable.addCell(Cell().add(Paragraph("Dis. %").setFont(boldFont)))
            productTable.addCell(Cell().add(Paragraph("Dis. Amt").setFont(boldFont)))
            productTable.addCell(Cell().add(Paragraph("After Dis.").setFont(boldFont)))
            productTable.addCell(Cell().add(Paragraph("GST").setFont(boldFont)))
            productTable.addCell(Cell().add(Paragraph("Net Amt").setFont(boldFont)))
            // Add some rows

            orderDetails.data.forEach { item ->
                productTable.addCell(Cell().add(Paragraph(item.productCode)))
                productTable.addCell(Cell().add(Paragraph(item.productName)))
                productTable.addCell(Cell().add(Paragraph(item.quantity.toString())))
                productTable.addCell(Cell().add(Paragraph(item.mrp.toString())))
                productTable.addCell(Cell().add(Paragraph(item.discountPercentage.toString())))
                productTable.addCell(Cell().add(Paragraph(item.discountAmount.toString())))
                productTable.addCell(Cell().add(Paragraph(item.afterDiscount.toString())))
                productTable.addCell(Cell().add(Paragraph(item.vatAmount.toString())))
                productTable.addCell(
                    Cell().add(
                        Paragraph(item.netAmount.toString()).setTextAlignment(
                            TextAlignment.RIGHT
                        )
                    )
                )
            }

            document.add(productTable)

            document.add(Div().setHeight(5f))

            val tableTotal = Table(
                floatArrayOf(
                    7f,
                    1f,
                    1f
                )
            ).useAllAvailableWidth().setFontSize(normalFontSize).setFont(boldFont)

            tableTotal.addCell(Cell().add(Paragraph(orderDetails.inWords)))
            tableTotal.addCell(Cell().add(Paragraph("Total")))
            tableTotal.addCell(Cell().add(Paragraph(String.format("%.2f", orderDetails.data.sumOf {
                it.netAmount
            })).setTextAlignment(TextAlignment.RIGHT)))

            document.add(tableTotal)

            val signTable = Table(
                floatArrayOf(
                    1f
                )
            ).setWidth(100f).setTextAlignment(TextAlignment.RIGHT)

            val signBitmap = base64ToImage(orderDetails.signature)
            val signStream = ByteArrayOutputStream()
            signBitmap.compress(Bitmap.CompressFormat.PNG, 100, signStream)
            val signImage = Image(ImageDataFactory.create(signStream.toByteArray()))
            signImage.setHeight(80f)
            signImage.setWidth(80f)
            signImage.setHorizontalAlignment(HorizontalAlignment.CENTER)

            signTable.addCell(Cell().add(signImage).setBorder(Border.NO_BORDER))
            signTable.addCell(
                Cell().add(
                    Paragraph("Signature").setFont(boldFont).setFontSize(12f)
                        .setTextAlignment(TextAlignment.CENTER)
                ).setBorder(Border.NO_BORDER)
            )

            document.add(signTable)


            Toast.makeText(
                context, "PDF saved at ${file.path}", Toast.LENGTH_SHORT
            ).show()
        } catch (ex: Exception) {
            Log.d("dataxx", "Error generating PDF: ${ex.message}")
        }

        pdfDocument.close()
    }


    openPdf(context = context, file = file)

}

fun openPdf(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(
            context, "Error opening PDF: ${e.message}", Toast.LENGTH_SHORT
        ).show()
        Log.d("dataxx", "Error opening PDF: ${e.message}")
    }

}

@SuppressLint("SimpleDateFormat")
private fun generateFileName(fileName: String): String {
    return fileName + SimpleDateFormat("yyMMddHHmmss").format(Calendar.getInstance().time)
}