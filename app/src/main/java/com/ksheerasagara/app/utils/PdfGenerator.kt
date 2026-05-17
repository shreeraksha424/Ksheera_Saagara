package com.ksheerasagara.app.utils

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import androidx.core.content.FileProvider
import com.ksheerasagara.app.data.local.dao.ExpenseCategoryTotal
import java.io.File
import java.io.FileOutputStream

object PdfGenerator {
    fun generateAndSharePdf(
        context: Context,
        totalIncome: Double,
        totalExpense: Double,
        netProfit: Double,
        expenseCategories: List<ExpenseCategoryTotal>
    ) {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
        val page = document.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            textSize = 24f
            isFakeBoldText = true
            color = Color.BLACK
        }
        val normalPaint = Paint().apply {
            textSize = 16f
            color = Color.BLACK
        }

        var currentY = 50f
        canvas.drawText("Ksheera-Sagara Monthly Report", 50f, currentY, titlePaint)
        currentY += 40f

        canvas.drawText("Financial Summary", 50f, currentY, titlePaint.apply { textSize = 20f })
        currentY += 30f
        
        canvas.drawText("Total Income: ${CurrencyUtils.format(totalIncome)}", 50f, currentY, normalPaint)
        currentY += 30f
        
        canvas.drawText("Total Expense: ${CurrencyUtils.format(totalExpense)}", 50f, currentY, normalPaint)
        currentY += 30f
        
        canvas.drawText("Net Profit/Loss: ${CurrencyUtils.format(netProfit)}", 50f, currentY, normalPaint.apply {
            color = if (netProfit >= 0) Color.rgb(0, 150, 0) else Color.RED
            isFakeBoldText = true
        })
        currentY += 50f

        normalPaint.color = Color.BLACK
        normalPaint.isFakeBoldText = false

        canvas.drawText("Expense Breakdown", 50f, currentY, titlePaint.apply { textSize = 20f })
        currentY += 30f

        expenseCategories.forEach { category ->
            canvas.drawText("${category.category}: ${CurrencyUtils.format(category.total)}", 50f, currentY, normalPaint)
            currentY += 25f
        }

        document.finishPage(page)

        val file = File(context.cacheDir, "Ksheera_Sagara_Report.pdf")
        try {
            document.writeTo(FileOutputStream(file))
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            document.close()
        }

        sharePdf(context, file)
    }

    private fun sharePdf(context: Context, file: File) {
        val uri: Uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        
        context.startActivity(Intent.createChooser(intent, "Share Monthly Report"))
    }
}
