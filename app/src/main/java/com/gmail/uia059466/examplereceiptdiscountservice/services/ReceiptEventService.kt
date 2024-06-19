package com.gmail.uia059466.examplereceiptdiscountservice.services

import android.content.ComponentName
import ru.evotor.framework.core.IntegrationService
import ru.evotor.framework.core.action.event.receipt.discount_required.ReceiptDiscountRequiredEvent
import ru.evotor.framework.core.action.event.receipt.discount_required.ReceiptDiscountRequiredEventProcessor
import ru.evotor.framework.core.action.event.receipt.discount_required.ReceiptDiscountRequiredEventResult
import ru.evotor.framework.core.action.processor.ActionProcessor


class ReceiptEventService : IntegrationService() {
    override fun createProcessors(): Map<String, ActionProcessor> {
        val map = HashMap<String, ActionProcessor>()
        map[ReceiptDiscountRequiredEvent.NAME_SELL_RECEIPT] = eventProcessor
        return map
    }


    private val eventProcessor = object : ReceiptDiscountRequiredEventProcessor() {
        override fun call(action: String, event: ReceiptDiscountRequiredEvent, callback: Callback) {
            val name = ComponentName(
                applicationContext,
                PaymentScreenService::class.java
            )
            callback.onResult(ReceiptDiscountRequiredEventResult(name))
        }
    }
}
