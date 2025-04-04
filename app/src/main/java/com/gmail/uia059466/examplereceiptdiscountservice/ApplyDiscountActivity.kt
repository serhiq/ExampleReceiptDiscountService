package com.gmail.uia059466.examplereceiptdiscountservice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import org.json.JSONObject
import ru.evotor.framework.core.IntegrationAppCompatActivity
import ru.evotor.framework.core.action.event.receipt.changes.position.PositionEdit
import ru.evotor.framework.core.action.event.receipt.changes.receipt.SetExtra
import ru.evotor.framework.core.action.event.receipt.discount.ReceiptDiscountEvent
import ru.evotor.framework.core.action.event.receipt.discount.ReceiptDiscountEventResult
import ru.evotor.framework.receipt.Position
import ru.evotor.framework.receipt.ReceiptApi
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.jvm.java

class TemporyInputActivity : IntegrationAppCompatActivity(){
    private val event
        get() = ReceiptDiscountEvent.create(sourceBundle) ?: throw RuntimeException("Отсутствует исходное событие.")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setIntegrationResult(makeResult())
            finish()
    }

    private fun makeResult() : ReceiptDiscountEventResult {
        val receipt = ReceiptApi.getReceipt(this, event.receiptUuid)  ?: throw IllegalStateException("Receipt not found")
        val positionsWithDiscount = receipt.getPositions().map { sourcePosition ->
            val newPrice = sourcePosition.price - sourcePosition.price * BigDecimal(0.1)

            Position.Builder.copyFrom(sourcePosition)
                .setPriceWithDiscountPosition(newPrice.setScale(0, RoundingMode. HALF_UP))
                .build().let {
                    PositionEdit(it)
                }

        }



        return ReceiptDiscountEventResult(
            BigDecimal.ZERO,
            SetExtra(JSONObject()),
            positionsWithDiscount,
            null
        )
    }


    companion object {
        @JvmStatic
        fun start(context: Context): Intent {
            return Intent(context,TemporyInputActivity::class.java)
        }
    }
}