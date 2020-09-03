package com.ekenya.echama.ui.group


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ekenya.echama.R
import com.ekenya.echama.adapter.GroupFilesAdapter
import com.ekenya.echama.adapter.RegionAdapter
import com.ekenya.echama.inc.AppInfo
import com.ekenya.echama.ui.register.RegionCallback
import com.ekenya.echama.util.DataUtil
import com.ekenya.echama.viewModel.GroupViewModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URI
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class UploadDocumentFragment : Fragment() {
    lateinit var ivAddChairPerson:ImageView
    lateinit var ivAddSecretary:ImageView
    lateinit var ivAddTreasurer:ImageView
    lateinit var ivAddDocument:ImageView
    lateinit var tvAddDocument: TextView
    lateinit var btnInviteMembers: Button
    lateinit var rcvDocument: RecyclerView
    lateinit var groupViewModel: GroupViewModel

    val MY_PERMISSIONS_REQUEST_CAMERA = 11
    val IMAGE_PICK_CODE = 999
    val CAMERA = 2
    companion object {
        private val IMAGE_DIRECTORY = "/echama"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val rootView= inflater.inflate(R.layout.fragment_upload_document, container, false) as View
        ivAddChairPerson = rootView.findViewById(R.id.ivAddChairPerson)
        ivAddSecretary = rootView.findViewById(R.id.ivAddSecretary)
        ivAddTreasurer =  rootView.findViewById(R.id.ivAddTreasurer)
        ivAddDocument =  rootView.findViewById(R.id.ivAddDocument)
        tvAddDocument =  rootView.findViewById(R.id.tvAddDocument)
        rcvDocument =  rootView.findViewById(R.id.rcvDocument)
        btnInviteMembers =  rootView.findViewById(R.id.btnInviteMembers)
        groupViewModel = ViewModelProvider(this).get(GroupViewModel::class.java)
        val contentJson = arguments?.getString("content")
        val bundle = Bundle()
        bundle.putString("content",contentJson)

        ivAddChairPerson.setOnClickListener {
            bundle.putString("rolename","chairperson")
            bundle.putInt("roleId",1)
            findNavController().navigate(R.id.nav_select_official,bundle)  }

        tvAddDocument.setOnClickListener {
            if(checknGrantPermmision()) {
               // takePhotoFromCamera()
                launchGallery()
            }
        }
        ivAddDocument.setOnClickListener {
            if(checknGrantPermmision()) {
                launchGallery()
                //takePhotoFromCamera()
            }
        }
        btnInviteMembers.setOnClickListener {
            findNavController().navigateUp()
        }

        GlobalScope.launch(Dispatchers.Main) {
            //loadRoles()
        }
        initRV()
        checknGrantPermmision()
        return rootView
    }

    private fun initRV() {
        rcvDocument.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        val adapter = GroupFilesAdapter(this)
        rcvDocument.adapter = adapter
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }
    private fun launchGallery() {

      //  val intent = Intent(Intent.ACTION_PICK)
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val uri = data?.data
            if (uri != null) {
                uploadFile(uri.path.toString())
            }
        }
        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            //imageview!!.setImageBitmap(thumbnail)
            uploadFile(saveImage(thumbnail))
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        Timber.v(this.requireContext().getExternalFilesDir(IMAGE_DIRECTORY)?.absolutePath)
        Timber.v((Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        val wallpaperDirectory = File(this.requireContext().getExternalFilesDir(IMAGE_DIRECTORY)?.absolutePath!!)
           // (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Timber.v("fee  "+wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdirs()
        }

        try
        {
            Timber.v("heel"+wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .timeInMillis).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this.requireContext(),
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            Timber.v("TAG File Saved::---> ${f.getAbsolutePath()}" )

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }
    private fun uploadFile(filePath: String) {
        val file = File(filePath)
    //  var grpFilesAr : ArrayList<MultipartBody.Part> = ArrayList<MultipartBody.Part>()
        DataUtil.grpFilesAr.add(file)
        rcvDocument.adapter?.notifyDataSetChanged()

    }
    fun checknGrantPermmision():Boolean{

        val permission = ContextCompat.checkSelfPermission(activity!!.applicationContext,
            Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Timber.v("Permission to record denied")
            makePermisionRequest()
            return false
        }
        return  true
    }
    private fun makePermisionRequest() {
        requestPermissions( arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE),
            MY_PERMISSIONS_REQUEST_CAMERA)
       // ActivityCompat.requestPermissions(activity!!,
       //     arrayOf(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE),
        //    MY_PERMISSIONS_REQUEST_CAMERA)
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        Timber.v("MY_PERMISSIONS_REQUEST_CAMERA ")
        when (requestCode) {

            MY_PERMISSIONS_REQUEST_CAMERA -> {
                Timber.v("MY_PERMISSIONS_REQUEST_CAMERA ")
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Timber.v("accepted")
                } else {
                    Timber.v("MY_PERMISSIONS_REQUEST_CAMERA denied")
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    findNavController().navigateUp()
                  //  findNavController().navigate(R.id.nav_home)
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                Timber.v("Ignore all other requests ")
                // Ignore all other requests.
            }
        }
    }


}
