package in.public_static.carin.paniar.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.eschao.android.widget.elasticlistview.ElasticListView;
import com.eschao.android.widget.elasticlistview.LoadFooter;
import com.eschao.android.widget.elasticlistview.OnLoadListener;
import com.eschao.android.widget.elasticlistview.OnUpdateListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;

import in.public_static.carin.paniar.App;
import in.public_static.carin.paniar.R;
import in.public_static.carin.paniar.activity.ActivityCompatParent;
import in.public_static.carin.paniar.activity.ActivityOrder;
import in.public_static.carin.paniar.activity.ActivityPopUpMain;
import in.public_static.carin.paniar.calender.CivilDate;
import in.public_static.carin.paniar.calender.DateConverter;
import in.public_static.carin.paniar.calender.PersianDate;
import in.public_static.carin.paniar.cunstractor.BimeItem;
import in.public_static.carin.paniar.utils.Constants;
import in.public_static.carin.paniar.utils.Statics;
import in.public_static.carin.paniar.utils.Utils;

public class FragmentWaitingForApprove extends Fragment implements OnUpdateListener, OnLoadListener {

    ElasticListView lst;
    ArrayList<BimeItem> items;
    ItemAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_waiting_for_approve, container, false);
        lst = (ElasticListView) view.findViewById(R.id.lst);
        lst.setHorizontalFadingEdgeEnabled(true);
        items = new ArrayList<>();
        adapter = new ItemAdapter(App.context, items);
        lst.setAdapter(adapter);
        lst.enableLoadFooter(true)
                .getLoadFooter().setLoadAction(LoadFooter.LoadAction.RELEASE_TO_LOAD);
        lst.setOnUpdateListener(this)
                .setOnLoadListener(this);
        lst.requestUpdate();
        TextView img_info = (TextView) view.findViewById(R.id.img_info);
        img_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(App.context, ActivityPopUpMain.class);
                intent.putExtra("type", "waiting_for_approve");
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        lst.requestUpdate();
    }

    @Override
    public void onUpdate() {
        // do update action
        StringRequest request = new StringRequest(Request.Method.GET, App.webServiceUrl + "bime_api/not_approved/token/" + Utils.getInstance().getUserToken(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("volley_response", "onResponse: waiting :" + response);
                items.clear();
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        BimeItem item = new BimeItem();
                        JSONObject object = array.getJSONObject(i);
                        if (object.getInt("type") == Constants._TYPE_HARIGH_MASKOONI_ALBORZ) {
                            JSONObject harigh = object.getJSONObject("harigh");
                            item.setBime_id(harigh.getInt("bime_id"));
                            item.setC_kind(harigh.getInt("customer_kind"));
                            item.setC_name(URLDecoder.decode(harigh.getString("customer_name"), "UTF-8").replace("%20", " "));
                            item.setC_family(URLDecoder.decode(harigh.getString("customer_family"), "UTF-8").replace("%20", " "));
                            item.setC_gender(harigh.getInt("customer_gender"));
                            item.setC_address(URLDecoder.decode(harigh.getString("customer_address"), "UTF-8").replace("%20", " "));
                            item.setC_c_code(harigh.getString("customer_country_code"));
                            item.setC_cell(harigh.getString("customer_cell"));
                            item.setC_tel(harigh.getString("customer_tel"));
                            item.setT_city(harigh.getInt("target_city"));
                            item.setT_address(URLDecoder.decode(harigh.getString("target_address"), "UTF-8").replace("%20", " "));
                            item.setT_p_code(harigh.getString("target_postal_code"));
                            item.setT_price(harigh.getString("target_price"));
                            item.setT_stuff_price(harigh.getString("target_stuff_price"));
                            item.setT_type(harigh.getInt("target_type"));
                            item.setT_age(harigh.getInt("target_age"));
                            item.setBime_process_state(harigh.getInt("bime_process_state"));
                            item.setBime_start_time(harigh.getString("bime_start_time"));
                            item.setBime_end_time(harigh.getString("bime_end_time"));
                            item.setBime_pay(harigh.getString("pay"));
                            String server_time = harigh.getString("time");
                            String date = "آیتم تایید نشده";
                            if (server_time != "null" && server_time != null) {
                                server_time = server_time + "000";
                                Long millis = Long.parseLong(server_time);
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(millis);
                                CivilDate civilDate = new CivilDate(calendar);
                                PersianDate persianDate = DateConverter.civilToPersian(civilDate);
                                date = persianDate.getYear() + "/" + persianDate.getMonth() + "/" + persianDate.getDayOfMonth();
                            }
                            item.setItem_time_careation(date);
                            item.setPost_state(harigh.getInt("post"));

                            JSONObject seil = object.getJSONObject("seil");
                            item.setSeil_process_state(seil.getInt("seil_process_state"));
                            item.setSeil_pay(seil.getString("pay"));
                            item.setSeil_start_time(seil.getString("bime_start_time"));
                            item.setSeil_end_time(seil.getString("bime_end_time"));

                            JSONObject toofan = object.getJSONObject("toofan");
                            item.setToofan_process_state(toofan.getInt("toofan_process_state"));
                            item.setToofan_pay(toofan.getString("pay"));
                            item.setToofan_start_time(toofan.getString("bime_start_time"));
                            item.setToofan_end_time(toofan.getString("bime_end_time"));

                            JSONObject zelzele = object.getJSONObject("zelzele");
                            item.setZelzele_process_state(zelzele.getInt("zelzele_process_state"));
                            item.setZelzele_pay(zelzele.getString("pay"));
                            item.setZelzele_start_time(zelzele.getString("bime_start_time"));
                            item.setZelzele_end_time(zelzele.getString("bime_end_time"));
                            items.add(item);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    lst.notifyUpdated();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                lst.notifyUpdated();
                Log.d("volley_response", "onErrorResponse: " + error.getMessage());
            }
        });
        int socketTimeout = 15000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        request.setRetryPolicy(policy);
        App.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onLoad() {
        // do load action
        lst.notifyLoaded();
    }

    static class ViewHolder {
        public TextView txtPostalCode_header;
        public TextView txtDate_header;
        public TextView txtFactorNo_header;
        private LinearLayout llBacLayout;
        private TextView txtItemTitle;
        private TextView txtItemPostalCode;
        private TextView txtFactorNo;
        private TextView txtBimeEnabledList;
        private TextView txtBimewaitingList;
        private TextView txtBimeAppliedList;
        private TextView txtDsc;
        private TextView btnPay;
        private ImageView imgItemLogo;
        private TextView txtDateDsc;
    }

    public class ItemAdapter extends ArrayAdapter<BimeItem> {


        public ItemAdapter(Context context, ArrayList<BimeItem> items) {
            super(context, R.layout.bime_item_waiting_for_approve, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            final ViewHolder holder;

            // Get the data item for this position
            final BimeItem item = getItem(position);

            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.bime_item_waiting_for_approve, parent, false);
                holder = new ViewHolder();
                holder.llBacLayout = (LinearLayout) convertView.findViewById(R.id.llBacLayout);
                holder.imgItemLogo = (ImageView) convertView.findViewById(R.id.imgItemLogo);
                holder.txtItemTitle = (TextView) convertView.findViewById(R.id.txtItemTitle);
                holder.txtItemPostalCode = (TextView) convertView.findViewById(R.id.txtItemPostalCode);
                holder.txtFactorNo = (TextView) convertView.findViewById(R.id.txtFactorNo);
                holder.txtBimeEnabledList = (TextView) convertView.findViewById(R.id.txtBimeEnabledList);
                holder.txtBimewaitingList = (TextView) convertView.findViewById(R.id.txtBimewaitingList);
                holder.txtBimeAppliedList = (TextView) convertView.findViewById(R.id.txtBimeAppliedList);
                holder.txtDsc = (TextView) convertView.findViewById(R.id.txtDsc);
                holder.btnPay = (TextView) convertView.findViewById(R.id.btn);
                holder.txtPostalCode_header = (TextView) convertView.findViewById(R.id.txtPostalCode_header);
                holder.txtDate_header = (TextView) convertView.findViewById(R.id.txtDate_header);
                holder.txtFactorNo_header = (TextView) convertView.findViewById(R.id.txtFactorNo_header);
                holder.txtDateDsc = (TextView) convertView.findViewById(R.id.txtDateDsc);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // Lookup view for data population
//            holder.llBacLayout.setBackgroundResource(R.drawable.background_gray_with_shadow);
//            holder.llBacLayout.setPadding(20, 20, 20, 20);
//            holder.txtDsc.setTextColor(Color.parseColor("#ffffff"));
//            holder.txtItemPostalCode.setTextColor(Color.parseColor("#ffffff"));
//            holder.txtBimeEnabledList.setTextColor(Color.parseColor("#ffffff"));
            Utils utils = Utils.getInstance();
            utils.prepareTextView(holder.txtItemTitle);
            utils.prepareTextView(holder.txtItemPostalCode);
            utils.prepareTextView(holder.txtFactorNo);
            utils.prepareTextView(holder.txtBimeEnabledList);
            utils.prepareTextView(holder.txtBimewaitingList);
            utils.prepareTextView(holder.txtBimeAppliedList);
            utils.prepareTextView(holder.txtDsc);
            holder.btnPay.setTypeface(ActivityCompatParent.type);
            holder.txtPostalCode_header.setTypeface(ActivityCompatParent.type);
            holder.txtDate_header.setTypeface(ActivityCompatParent.type);
            holder.txtFactorNo_header.setTypeface(ActivityCompatParent.type);
            holder.txtItemPostalCode.setTypeface(ActivityCompatParent.type);
            holder.txtFactorNo.setTypeface(ActivityCompatParent.type);
            holder.txtDateDsc.setTypeface(ActivityCompatParent.type);


            // Populate the data into the template view using the data object
            holder.txtItemPostalCode.setText(item.getT_p_code());
            try {
                holder.txtFactorNo.setText(String.valueOf(item.getBime_id()));
            } catch (Exception e) {
            }
            StringBuilder builder = new StringBuilder();
            if (item.getBime_process_state() == Statics._PAYED) {
                builder.append("  حریق ");
            }
            if (item.getSeil_process_state() == Statics._PAYED) {
                builder.append(" سیل ");
            }
            if (item.getToofan_process_state() == Statics._PAYED) {
                builder.append(" طوفان ");
            }
            if (item.getZelzele_process_state() == Statics._PAYED) {
                builder.append(" زلزله ");
            }
            builder.append(" خرید شد ");
            holder.txtBimeEnabledList.setText(builder.toString());

            StringBuilder sbWaitingtoApply = new StringBuilder();
            if (item.getBime_process_state() == Statics._PROCESS) {
                sbWaitingtoApply.append("  حریق ");
            }
            if (item.getSeil_process_state() == Statics._PROCESS) {
                sbWaitingtoApply.append(" سیل ");
            }
            if (item.getToofan_process_state() == Statics._PROCESS) {
                sbWaitingtoApply.append(" طوفان ");
            }
            if (item.getZelzele_process_state() == Statics._PROCESS) {
                sbWaitingtoApply.append(" زلزله ");
            }
            sbWaitingtoApply.append(" در انتظار تایید ");
            holder.txtBimewaitingList.setText(sbWaitingtoApply.toString());

            StringBuilder sbApplied = new StringBuilder();

            if (item.getBime_process_state() == Statics._GRANTED || item.getBime_process_state() == Statics._PERMITTED) {
                sbApplied.append("  حریق ");
            }
            if (item.getSeil_process_state() == Statics._GRANTED || item.getSeil_process_state() == Statics._PERMITTED) {
                sbApplied.append(" سیل ");
            }
            if (item.getToofan_process_state() == Statics._GRANTED || item.getToofan_process_state() == Statics._PERMITTED) {
                sbApplied.append(" طوفان ");
            }
            if (item.getZelzele_process_state() == Statics._GRANTED || item.getZelzele_process_state() == Statics._PERMITTED) {
                sbApplied.append(" زلزله ");
            }
            sbApplied.append(" تایید شد ");
            holder.txtBimeAppliedList.setText(sbApplied.toString());

            StringBuilder sbDenied = new StringBuilder();

            if (item.getBime_process_state() == Statics._DENIED) {
                sbDenied.append("  حریق ");
            }
            if (item.getSeil_process_state() == Statics._DENIED) {
                sbDenied.append(" سیل ");
            }
            if (item.getToofan_process_state() == Statics._DENIED) {
                sbDenied.append(" طوفان ");
            }
            if (item.getZelzele_process_state() == Statics._DENIED) {
                sbDenied.append(" زلزله ");
            }
            sbDenied.append(" رد شد ");


            holder.txtDsc.setText(sbDenied.toString());
            holder.btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), ActivityOrder.class);
                    intent.putExtra("obj", item);
                    startActivity(intent);
                }
            });

            holder.txtDateDsc.setText(item.getItem_time_careation());
            // Return the completed view to render on screen
            return convertView;
        }


    }

}