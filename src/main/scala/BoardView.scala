package com.napthats.android.everboard

import _root_.android.graphics.Canvas
import _root_.android.view.View
import _root_.android.content.Context
import _root_.android.util.AttributeSet
import _root_.android.os.Bundle
import _root_.android.content.res.TypedArray
import _root_.android.graphics.Paint
import _root_.android.graphics.Color


class BoardView(context: Context, attrs:AttributeSet) extends View(context, attrs) {
  override def onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    val paint = new Paint()
    paint.setColor(Color.RED)

    canvas.drawCircle(100, 300, 20, paint)
  }
}
