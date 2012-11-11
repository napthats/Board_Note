package com.napthats.android.everboard

import _root_.android.graphics.Canvas
import _root_.android.view.View
import _root_.android.content.Context
import _root_.android.util.AttributeSet
import _root_.android.os.Bundle
import _root_.android.content.res.TypedArray
import _root_.android.graphics.Paint
import _root_.android.graphics.Color
import _root_.android.view.MotionEvent
import _root_.android.widget.Toast

import _root_.org.metalev.multitouch.controller.MultiTouchController;
import _root_.org.metalev.multitouch.controller.MultiTouchController.MultiTouchObjectCanvas;
import _root_.org.metalev.multitouch.controller.MultiTouchController.PointInfo;
import _root_.org.metalev.multitouch.controller.MultiTouchController.PositionAndScale;


object BoardView {
  var base_x = 0.0
  var base_y = 0.0
  var base_scale = 1.0

  def window2WorldPosition(x_orig: Double, y_orig: Double): {val x: Double; val y: Double} = {
    new {val x = (x_orig / base_scale) + base_x; val y = (y_orig / base_scale) + base_y}
  }

  def world2WindowPosition(x_orig: Double, y_orig: Double): {val x: Double; val y: Double} = {
    new {val x = (x_orig - base_x) * base_scale; val y = (y_orig - base_y) * base_scale}
  }
}
  

class BoardView(context: Context, attrs:AttributeSet) extends View(context, attrs) with MultiTouchObjectCanvas[Page]{
  val multiTouchController = new MultiTouchController[Page](this)

  PageContainer.add(Page("test1", 200.0, 100.0, Color.BLUE))
  PageContainer.add(Page("test2", 400.0, 400.0, Color.RED))


  //for Canvas#drawRect
  private implicit def double2Float(x: Double): Float = x.toFloat


  override def onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    PageContainer.drawAll(canvas)
  }

  override def selectObject(page: Page, point: PointInfo) {
  }
  
  override def setPositionAndScale(page: Page, pos_scale: PositionAndScale, point: PointInfo): Boolean = {
    val pos = BoardView.window2WorldPosition(pos_scale.getXOff, pos_scale.getYOff)
    page.dragAndPinch(new {val x = pos.x; val y = pos.y; val scale: Double = pos_scale.getScale})
    invalidate()
    return true
  }

  override def getPositionAndScale(page: Page, pos_scale: PositionAndScale) {
    pos_scale.set(page.winX, page.winY, true, page.scale, false, page.scale, page.scale, false, 0)
  }

  override def getDraggableObjectAtPoint(point: PointInfo): Page = {
    val pos = BoardView.window2WorldPosition(point.getX, point.getY)
    PageContainer.getAtPoint(pos).getOrElse(Page.dummy)
  }

  override def onTouchEvent(event: MotionEvent): Boolean = multiTouchController.onTouchEvent(event)
  
}
