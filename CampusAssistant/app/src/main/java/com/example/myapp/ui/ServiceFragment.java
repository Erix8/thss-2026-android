package com.example.myapp.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapp.R;
public class ServiceFragment extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);

        setItem(view, R.id.item1, android.R.drawable.ic_menu_edit, "报到注册",
                "线上完成身份核验、资料提交与到校登记，快速查看进度并按提示补全信息。");
        setItem(view, R.id.item2, android.R.drawable.ic_menu_agenda, "校园卡",
                "办理新卡、绑定支付、查询余额与消费记录，一站式完成充值和挂失操作。");
        setItem(view, R.id.item3, android.R.drawable.ic_menu_myplaces, "住宿信息",
                "查看宿舍楼栋、房间与床位安排，支持报修入口和入住须知快速查看。");
        setItem(view, R.id.item4, android.R.drawable.ic_dialog_map, "校园地图",
                "浏览教学楼、食堂、图书馆等关键地点，按路线导航更高效抵达目的地。");

        return view;
    }

    private void setItem(View view, int id, int iconRes, String title, String desc) {
        LinearLayout layout = view.findViewById(id);
        layout.removeAllViews();
        layout.setGravity(Gravity.CENTER_VERTICAL);

        ImageView icon = new ImageView(getContext());
        icon.setImageResource(iconRes);
        icon.setColorFilter(Color.parseColor("#7B1FA2"));
        LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(dp(26), dp(26));
        iconParams.setMarginEnd(dp(12));
        icon.setLayoutParams(iconParams);

        LinearLayout textContainer = new LinearLayout(getContext());
        textContainer.setOrientation(LinearLayout.VERTICAL);
        textContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView titleView = new TextView(getContext());
        titleView.setText(title);
        titleView.setTextSize(19);
        titleView.setTextColor(Color.parseColor("#7B1FA2"));
        titleView.setTypeface(titleView.getTypeface(), android.graphics.Typeface.BOLD);

        TextView descView = new TextView(getContext());
        descView.setText(desc);
        descView.setTextSize(14);
        descView.setTextColor(Color.parseColor("#666666"));
        descView.setLineSpacing(0, 1.15f);

        textContainer.addView(titleView);
        textContainer.addView(descView);

        layout.addView(icon);
        layout.addView(textContainer);

        layout.setOnClickListener(v ->
                Toast.makeText(getContext(), title, Toast.LENGTH_SHORT).show());
    }

    private int dp(int value) {
        return (int) (value * requireContext().getResources().getDisplayMetrics().density);
    }
}
