package com.ekenya.echama.ui.group

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import timber.log.Timber

class ContactHelper(val context: Context) : LoaderManager.LoaderCallbacks<Cursor> {

    private lateinit var mLoadContactComplete: ContactLoadComplete
    private var mArrayList = ArrayList<PhoneList>()
    private var mNameArrayList = ArrayList<String>()

    companion object {
        val projection = arrayOf(ContactsContract.Data.MIMETYPE, ContactsContract.Data.CONTACT_ID, ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Contactables.DATA, ContactsContract.CommonDataKinds.Contactables.TYPE, ContactsContract.Contacts.HAS_PHONE_NUMBER)
        val selection = ContactsContract.Data.MIMETYPE + " in (?, ?)"
        val selectionArgs = arrayOf(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
        val sortOrder = ContactsContract.Contacts.SORT_KEY_ALTERNATIVE
        val uri = ContactsContract.CommonDataKinds.Contactables.CONTENT_URI
    }

    fun getAllContacts(mFragment: Fragment, loadContactComplete: ContactLoadComplete) {
        mLoadContactComplete = loadContactComplete
        LoaderManager.getInstance(mFragment).initLoader(0, null, this)
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(context, uri, projection, selection, selectionArgs, sortOrder)
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onLoadFinished(loader: Loader<Cursor>, cursor: Cursor?) {
        Timber.v("onLoadFinished-->")

        if (cursor != null && !cursor.isClosed) {
            mArrayList.clear()
            while (cursor.moveToNext()) {
                val id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID))
                val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                var hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                var mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                hasPhone = if (hasPhone.equals("1", ignoreCase = true)) "true"
                else "false"

                if (!mNameArrayList.contains(name)) {
                    mNameArrayList.add(name)
                    if (hasPhone.equals("true", true)) {
                        val mPhoneList = PhoneList()
                        val data = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA)
                        mPhoneList.phoneNumber = mobile
                        mPhoneList.id = id
                        mPhoneList.name = name
                        mPhoneList.hasPhone = hasPhone.toBoolean()
                        mPhoneList.imagePath = getPhotoUri(context, id).toString()
                        mArrayList.add(mPhoneList)
                        Timber.v("helpercontacts %s",name+mobile)
                    }
                }
            }
            cursor.close()
            mLoadContactComplete.getAllContact(mArrayList, mNameArrayList)
        }
    }

    @SuppressLint("Recycle")
    private fun getPhotoUri(context: Context, id: Long): Uri? {
        val person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id)
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {

    }

    interface ContactLoadComplete {
        fun getAllContact(mContactArrayList: ArrayList<PhoneList>, mNameArrayList: ArrayList<String>)
    }

    open class ContactList(var id: Long = 0, var name: String = "", var hasPhone: Boolean = false, var path: String = "", var imagePath: String = "", var isInvite: Boolean = false)
    class PhoneList(var phoneId:String="",var phoneNumber:String="") : ContactList()

}