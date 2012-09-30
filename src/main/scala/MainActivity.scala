package com.napthats.android

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.Toast
import _root_.android.view.View
import _root_.android.os.Environment
import _root_.android.content.Intent
import _root_.android.net.Uri
import _root_.android.content.Context

import java.io.File
import java.io.IOException
import java.io.FileWriter
import java.io.PrintWriter


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
      EnexUtil.createNote(this, "app test", findView(TR.textview).getText().toString())
    })
  }
  
}

object EnexUtil {
  def createNote(act:Activity, title: String, text: String) {
    
    /*
    val dir = "/data/data/" + act.getPackageName + "/"
    val enexFile = new File(dir, "sample.enex")
    enexFile.setReadable(true, false)

    // Create a properly formatted ENEX file that defines the note we want created
    
    try {
        val out = new PrintWriter(new FileWriter(enexFile))
        out.print(ENEX_PREFIX_PART_ONE);
        out.print(title);
        out.print(ENEX_PREFIX_PART_TWO);
        out.print(NOTE_PREFIX);
        out.print("\n")
        out.print(text);
        out.print(NOTE_SUFFIX);
        out.print("\n")
        out.print(ENEX_SUFFIX);
        out.print("\n")
        out.close();
    }
    catch {
      case e: Exception => Toast.makeText(act, e.toString, Toast.LENGTH_SHORT).show()
    }

    val intent = new Intent();
    intent.setAction(Intent.ACTION_SEND);
    // Unlike sharing a file attachment, sharing an ENEX requires that you pass the file
    // in the data field, not the stream extra
    //intent.setDataAndType(Uri.fromFile(enexFile), TYPE_ENEX);
    //intent.setType("aa")
    intent.setDataAndType(_root_.android.net.Uri.fromParts("file", "///", ""), TYPE_ENEX);
    try {
      act.startActivity(Intent.createChooser(intent, "Share with:"));
    }
    catch {
      case e: Exception => Toast.makeText(act, "intent error", Toast.LENGTH_SHORT).show()
    }
    */
    val intent = new Intent()
    intent.setAction("com.evernote.action.CREATE_NEW_NOTE")
    intent.putExtra(Intent.EXTRA_TITLE, title)
    intent.putExtra(Intent.EXTRA_TEXT, text)
    intent.putExtra("QUICK_SEND", true)
    act.startActivity(intent)
  }  

  val TYPE_ENEX = "application/enex"
    
  val APP_DATA_PATH = "/Android/data/com.napthats.android/files/"

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

