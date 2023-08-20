package com.example.marketplace.Adapter.Listeners

import com.example.marketplace.Model.Chat

class OnChatClickListener (val clickListener: (chat:Chat) -> Unit) {
    fun onClick(chat:Chat) = clickListener(chat)
}