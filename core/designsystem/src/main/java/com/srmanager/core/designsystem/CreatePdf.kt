package com.srmanager.core.designsystem

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.LineSeparator
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.TextAlignment
import com.srmanager.order_domain.model.OrderDetailsResponse
import com.srmanager.order_domain.model.OrderItemResponse
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

fun generatePdf2(context: Context, orderDetails: OrderDetailsResponse) {
    val dir = File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "TESTPDF"
    )

    if (!dir.exists()) {
        dir.mkdir()
    }

    val file = File(dir, generateFileName("test") + ".pdf")

    val pdfWriter = PdfWriter(file)
    val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(pdfWriter)
    val document = Document(pdfDocument)

    // Custom Fonts
    val regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA)
    val boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)

    try {
        // Add Title
        val title = Paragraph("Custom PDF Title")
            .setFont(boldFont)
            .setFontSize(18f)
            .setTextAlignment(TextAlignment.CENTER)
        document.add(title)

        // Add a Line Separator
        val lineSeparator = LineSeparator(SolidLine())
        document.add(lineSeparator)

        // Add a Table
        val table = Table(
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
        ).useAllAvailableWidth()// 9 columns
        table.addCell(Cell().add(Paragraph("Code").setFont(boldFont)))
        table.addCell(Cell().add(Paragraph("Title").setFont(boldFont)))
        table.addCell(Cell().add(Paragraph("Qty").setFont(boldFont)))
        table.addCell(Cell().add(Paragraph("Unit Price").setFont(boldFont)))
        table.addCell(Cell().add(Paragraph("Dis. %").setFont(boldFont)))
        table.addCell(Cell().add(Paragraph("Dis. Amt").setFont(boldFont)))
        table.addCell(Cell().add(Paragraph("After Dis.").setFont(boldFont)))
        table.addCell(Cell().add(Paragraph("GST").setFont(boldFont)))
        table.addCell(Cell().add(Paragraph("Net Amt").setFont(boldFont)))
        // Add some rows

        orderDetails.data.forEach { item ->
            table.addCell(Cell().add(Paragraph(item.productCode)))
            table.addCell(Cell().add(Paragraph(item.productName)))
            table.addCell(Cell().add(Paragraph(item.quantity.toString())))
            table.addCell(Cell().add(Paragraph(item.mrp.toString())))
            table.addCell(Cell().add(Paragraph(item.discountPercentage.toString())))
            table.addCell(Cell().add(Paragraph(item.discountAmount.toString())))
            table.addCell(Cell().add(Paragraph(item.afterDiscount.toString())))
            table.addCell(Cell().add(Paragraph(item.vatAmount.toString())))
            table.addCell(Cell().add(Paragraph(item.netAmount.toString())))
        }
        document.add(table)

        Toast.makeText(
            context, "PDF saved at ${file.path}", Toast.LENGTH_SHORT
        ).show()
    } catch (ex: Exception) {
        Log.d("dataxx", "Error generating PDF: ${ex.message}")
    }

    pdfDocument.close()

    // Open the PDF
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