package org.ash.test.uf.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.ash.test.uf.R
import org.ash.test.uf.util.Constants
import org.ash.test.uf.viewmodel.DetailViewModel


class UserDetailActivity : AppCompatActivity() {

    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id =  intent.getIntExtra(Constants.INTENT_USER_ID, -1)

        detailViewModel.getUserById(id).observe(this@UserDetailActivity, Observer {

            Picasso.Builder(this@UserDetailActivity).build()
                .load(it?.large)
                .resize(520, 520)
                .centerCrop()
                .into(large)
            u_title.text = getString(R.string.label_title,it?.title)
            full_name.text = getString(R.string.label_title,it?.getFullname())
            gender.text = getString(R.string.label_gender,it?.gender)
            dob.text = getString(R.string.label_dob,it?.getDOBString())
            phone.text = getString(R.string.label_phone,it?.cell)
            email.text = getString(R.string.label_email,it?.email)
            address.text = getString(R.string.label_address,it?.getAddressString())
        })
    }
}