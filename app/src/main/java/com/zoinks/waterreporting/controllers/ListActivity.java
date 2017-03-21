package com.zoinks.waterreporting.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoinks.waterreporting.R;
import com.zoinks.waterreporting.model.WaterReport;
import com.zoinks.waterreporting.model.WaterReportSvcProvider;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    private final WaterReportSvcProvider wrsp = WaterReportSvcProvider.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        assert recyclerView != null;
        if (getIntent().getBooleanExtra("ManagerList", false)) {
            recyclerView.setAdapter(new WaterReportRecyclerViewAdapter(wrsp.getQualityReports()));
        } else {
            recyclerView.setAdapter(new WaterReportRecyclerViewAdapter(wrsp.getReports()));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    /**
     * Custom adapter taking water reports and mapping their toString to a text field
     */
    public class WaterReportRecyclerViewAdapter
            extends RecyclerView.Adapter<WaterReportRecyclerViewAdapter.ViewHolder> {

        /**
         * Collection of the WaterReport items to be shown in this list
         */
        private final List<WaterReport> mReports;

        /**
         * set the items to be used by the adapter
         *
         * @param reportList the list of WaterReports to be displayed in the recycler view
         */
        public WaterReportRecyclerViewAdapter(List<WaterReport> reportList) {
            mReports = reportList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // set up the view for each individual item
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.report_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mWaterReport = mReports.get(position);

            // binding data
            holder.mReportView.setText(mReports.get(position).toString());

            // set up listener to handle if the user clicks on the list item
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, MainActivity.class);
//                    wrsp.setCurrentWaterReport(holder.mWaterReport);
//                    context.startActivity(intent);
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return mReports.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding b/t the model element (a WaterReport) and the two TextViews in the view
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView mReportView;
            public WaterReport mWaterReport;

            public ViewHolder(View view) {
                super(view);
                mReportView = (TextView) view.findViewById(R.id.report);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mReportView.getText() + "'";
            }
        }
    }
}
