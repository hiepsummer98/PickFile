package com.hiepsummer.mp4reader

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_pick.setOnClickListener() {
            var intent = Intent()
            intent.setType("video/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 101) {
                var uri: Uri? = data.data
                var selectedImage: String = getPath(uri!!)
                if (selectedImage != null) {
                    video_view.setVideoPath(selectedImage)
                    var mediaController: MediaController = MediaController(this)
                    video_view.setMediaController(mediaController)
                    video_view.start()
                }
            }
        }
    }

    private fun getPath(uri: Uri): String {
        var projectionAray = arrayOf(MediaStore.Video.Media.DATA)
        var cursor: Cursor? = applicationContext.contentResolver.query(uri, projectionAray, null, null, null)
        if (cursor != null) {
            var columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(columnIndex)
        } else {
            return ""
        }
    }
}
