package com.ekenya.echama.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

class ToastnDialog {
    companion object{
        fun toastSuccess(context:Context,msg:String){
            Toasty.success(context,msg,Toasty.LENGTH_SHORT,true).show()
           // Toasty.success(context, "Success!", Toast.LENGTH_SHORT, true).show();
        }
        /**
         * Toasty error message display
         */
        fun toastError(context:Context,msg:String) {
            Toasty.error(context,msg,Toast.LENGTH_SHORT,true).show()
        }
    }
}