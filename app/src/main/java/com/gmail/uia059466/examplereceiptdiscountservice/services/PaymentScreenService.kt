package com.gmail.uia059466.examplereceiptdiscountservice.services


import ru.evotor.framework.core.IntegrationService
import ru.evotor.framework.core.action.event.receipt.discount.ReceiptDiscountEvent
import ru.evotor.framework.core.action.event.receipt.discount.ReceiptDiscountEventProcessor
import ru.evotor.framework.core.action.event.receipt.discount.ReceiptDiscountEventResult
import ru.evotor.framework.core.action.processor.ActionProcessor
import java.math.BigDecimal

class PaymentScreenService : IntegrationService() {
    override fun createProcessors(): MutableMap<String, ActionProcessor> {
        return mutableMapOf(
            ReceiptDiscountEvent.NAME_SELL_RECEIPT to onDiscount
        )
    }

    private val onDiscount = object : ReceiptDiscountEventProcessor() {
        override fun call(action: String, event: ReceiptDiscountEvent, callback: Callback) {
//            Thread.sleep(600)
            callback.onResult(ReceiptDiscountEventResult(BigDecimal.TEN, null, emptyList(), null))
        }
    }
}