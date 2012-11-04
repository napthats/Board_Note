package com.napthats.android.everboard

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.Toast
import _root_.android.view.View
import _root_.android.content.Intent

import com.napthats.android.utils.ListenerImplicit._
import com.napthats.android.evernote.Evernote


class MainActivity extends Activity with TypedActivity {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)

    findView(TR.textview).setText("hi")

    findView(TR.test).setOnClickListener((_: View) => {
      Evernote.createNote(this, "app test", findView(TR.textview).getText().toString())
    })
  }
}

