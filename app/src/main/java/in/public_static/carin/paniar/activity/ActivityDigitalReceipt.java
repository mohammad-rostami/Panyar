package in.public_static.carin.paniar.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import java.util.Calendar;

import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.calender.CivilDate;
import in.public_static.carin.paniar.calender.DateConverter;
import in.public_static.carin.paniar.calender.PersianDate;
import in.public_static.carin.paniar.cunstractor.BimeItem;
import in.public_static.carin.paniar.databinding.ActivityDigitalReceiptBinding;

public class ActivityDigitalReceipt extends ActivityCompatParent {

    ActivityDigitalReceiptBinding root;
    BimeItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = DataBindingUtil.setContentView(this, R.layout.activity_digital_receipt);
        Bundle extras = getIntent().getExtras();
        item = (BimeItem) extras.getSerializable("obj");
        prepareViews();
        setViewFunctions();
        setUI();
    }

    @Override
    protected void setUI() {
        super.setUI();
//        Picasso.with(this).load(R.drawable.general_options).into(root.imgGeneralOptions);
        root.txtCodePeigiri1.setText("کد پیگیری: " + item.getBime_id());
        root.txtCodePeigiri2.setText("کد پیگیری: " + item.getBime_id());
        root.txtCodePeigiri3.setText("کد پیگیری: " + item.getBime_id());
        root.txtCodePeigiri4.setText("کد پیگیری: " + item.getBime_id());
        root.txtCodePeigiri5.setText("کد پیگیری: " + item.getBime_id());
        root.txtCodePeigiri6.setText("کد پیگیری: " + item.getBime_id());
        root.txtRegisterCode2.setText("کد ثبت: " + item.getBime_id());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(item.getBime_start_time()) * 1000);
        CivilDate civilDate = new CivilDate(calendar);
        PersianDate persianDate = DateConverter.civilToPersian(civilDate);
        root.txtRegisterDate1.setText("تاریخ ثبت: " + persianDate.getYear() + "/" + persianDate.getMonth() + "/" + persianDate.getDayOfMonth());
        root.txtRegisterDate3.setText("تاریخ ثبت: " + persianDate.getYear() + "/" + persianDate.getMonth() + "/" + persianDate.getDayOfMonth());


        root.txtSeller1.setText("بیمه گر: بیمه البرز");
        root.txtSeller2.setText("بیمه گر: بیمه البرز");
        root.txtSeller3.setText("بیمه گر: بیمه البرز");
        root.txtSeller4.setText("بیمه گر: بیمه البرز");
        root.txtSeller5.setText("بیمه گر: بیمه البرز");
        root.txtSeller6.setText("بیمه گر: بیمه البرز");

        root.txtCName1.setText("بیمه گذار: " + item.getC_name() + " " + item.getC_family());
        root.txtCustomerName2.setText("بیمه گذار: " + item.getC_name() + " " + item.getC_family());
        root.txtCustomerName3.setText("بیمه گذار: " + item.getC_name() + " " + item.getC_family());
        root.txtCustomerName4.setText("بیمه گذار: " + item.getC_name() + " " + item.getC_family());
        root.txtCustomerName5.setText("بیمه گذار: " + item.getC_name() + " " + item.getC_family());
        root.txtCustomerName6.setText("بیمه گذار: " + item.getC_name() + " " + item.getC_family());

        root.txtCCCode1.setText("کد ملی: " + item.getC_c_code());
        root.txtCCCode3.setText("کد ملی: " + item.getC_c_code());
        root.txtCCCode4.setText("کد ملی: " + item.getC_c_code());
        root.txtCCCode5.setText("کد ملی: " + item.getC_c_code());
        root.txtCCCode6.setText("کد ملی: " + item.getC_c_code());

        root.txtCustomerAddress2.setText("نشانی: " + item.getC_address());
        root.txtCAddress1.setText("نشانی: " + item.getC_address());
        root.txtCAddress3.setText("نشانی: " + item.getC_address());
        root.txtCustomerAddress4.setText("نشانی: " + item.getC_address());
        root.txtCustomerAddress5.setText("نشانی: " + item.getC_address());
        root.txtCustomerAddress6.setText("نشانی: " + item.getC_address());

        root.txtTAddress1.setText("محل مورد بیمه: " + item.getT_address());

        root.txtTPostalCode1.setText("کد پستی: " + item.getT_p_code());

        root.txtTPrice.setText("ساختمان و تاسیسات منزل مسکونی واقع در آدرس فوق جمعا به مبلغ:" + item.getT_price());
        root.txtTStuffPrice1.setText(":اساسیه و محتویات منزل مسکونی جمعا به مبلغ" + item.getT_stuff_price());

        root.txtTTotalPrice1.setText("جمع سرمایه مورد بیمه به عدد: " + (Integer.parseInt(item.getT_stuff_price()) + Integer.parseInt(item.getT_price())));
//        Picasso.with(this).load(R.drawable.harigh_options).into(root.imgHarighOptions);
//        if (item.getSeil_process_state() == Statics._PAYED) {
//            Picasso.with(this).load(R.drawable.seyl).into(root.imgSeilOptions);
//            calendar.setTimeInMillis(Long.parseLong(item.getSeil_start_time()) * 1000);
//            civilDate = new CivilDate(calendar);
//            persianDate = DateConverter.civilToPersian(civilDate);
//            root.txtRegisterDate4.setText("تاریخ ثبت: " + persianDate.getYear() + "/" + persianDate.getMonth() + "/" + persianDate.getDayOfMonth());
//            root.txtTPooshesh.setText(root.txtTPooshesh.getText().toString() + " سیل");
//        } else {
//            root.llPage4.setVisibility(View.GONE);
//        }
//        if (item.getToofan_process_state() == Statics._PAYED) {
//            Picasso.with(this).load(R.drawable.toofan).into(root.imgToofanOptions);
//            calendar.setTimeInMillis(Long.parseLong(item.getToofan_start_time()) * 1000);
//            civilDate = new CivilDate(calendar);
//            persianDate = DateConverter.civilToPersian(civilDate);
//            root.txtRegisterDate5.setText("تاریخ ثبت: " + persianDate.getYear() + "/" + persianDate.getMonth() + "/" + persianDate.getDayOfMonth());
//            root.txtTPooshesh.setText(root.txtTPooshesh.getText().toString() + " طوفان");
//        } else {
//            root.llPage5.setVisibility(View.GONE);
//        }
//        if (item.getZelzele_process_state() == Statics._PAYED) {
//            Picasso.with(this).load(R.drawable.zelzeleh).into(root.imgZelzeleOptions);
//            calendar.setTimeInMillis(Long.parseLong(item.getZelzele_start_time()) * 1000);
//            civilDate = new CivilDate(calendar);
//            persianDate = DateConverter.civilToPersian(civilDate);
//            root.txtRegisterDate6.setText("تاریخ ثبت: " + persianDate.getYear() + "/" + persianDate.getMonth() + "/" + persianDate.getDayOfMonth());
//            root.txtTPooshesh.setText(root.txtTPooshesh.getText().toString() + " زلزله");
//        } else {
//            root.llPage6.setVisibility(View.GONE);
//        }


    }
}
