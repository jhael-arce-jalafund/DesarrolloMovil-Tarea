package com.example.practicacomponentes.ui.components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import com.example.practicacomponentes.R

class PageSelectorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var selectedIndex = -1
    private val buttons = mutableListOf<Button>()

    init {
        orientation = HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.page_selector_view, this, true)
        createButtons()
    }

    private fun createButtons() {
        val container = findViewById<LinearLayout>(R.id.pageSelectorContainer)

        for (i in 1..5) {
            val button = Button(context).apply {
                text = i.toString()
                setBackgroundColor(Color.parseColor("#87CEFA")) // Azul claro
                setTextColor(Color.BLACK)
                textSize = 24f
                layoutParams = LayoutParams(200, 200).apply {
                    setMargins(16, 0, 16, 0)
                }

                setOnClickListener {
                    selectButton(i - 1)
                }
            }
            buttons.add(button)
            container.addView(button)
        }
    }

    private fun selectButton(index: Int) {
        if (selectedIndex != -1) {
            buttons[selectedIndex].setBackgroundColor(Color.parseColor("#87CEFA")) // azul claro
        }

        buttons[index].setBackgroundColor(Color.parseColor("#90EE90")) // verde claro
        selectedIndex = index
    }
}
