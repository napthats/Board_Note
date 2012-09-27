package com.napthats.android

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.Toast
import _root_.android.view.View
import _root_.android.os.Environment

import com.napthats.android.ListenerUtils._


class MainActivity extends Activity with TypedActivity {
  override def onCreate(bundle: Bundle) {
    super.onCreate(bundle)
    setContentView(R.layout.main)

    findView(TR.textview).setText("hi")
//    findView(TR.test).setOnClickListener(
//      new View.OnClickListener() {
//        def onClick(view: View) {
//          Toast.makeText(MainActivity.this, findView(TR.textview).getText(), Toast.LENGTH_SHORT).show()
//        }
//      }
//   )
    findView(TR.test).setOnClickListener((_: View) => {
      Toast.makeText(this, findView(TR.textview).getText(), Toast.LENGTH_SHORT).show()
    })
  }
  
}

object EnexUtil {
  val ENEX_PREFIX_PART_ONE =
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE en-export SYSTEM \"http://xml.evernote.com/pub/evernote-export.dtd\">" +
    "<en-export><note><title>"

  // The prefix for an ENEX file, after the note title but before the note content
  val ENEX_PREFIX_PART_TWO = "</title><content><![CDATA["

  // The suffix for an ENEX file, after the note content
  val ENEX_SUFFIX =
    "]]></content></note></en-export>"

  // The ENML prefix for note content
  val NOTE_PREFIX =
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
    "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">" +
    "<en-note>"

  // The ENML suffix for note content
  val NOTE_SUFFIX = "</en-note>"
}

