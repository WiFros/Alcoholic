package com.hanul.alcoholic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {
    Context context;
    private ArrayList<List_item> list_itemArrayList = new ArrayList<List_item>();

    public MyListAdapter(Context context, ArrayList<List_item> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    /**
     * 이 리스트뷰가 몇개의 아이템을 가지고있는지를 알려주는 함수입니다. 우리는 arraylist의 size(갯수) 만큼 가지고있으므로
     * return 0 ; ->      this.list_itemArrayList.size()
     * @return
     */
    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return list_itemArrayList.get(position);
    }

    /**
     * Object getItem(int position)은 현재 어떤 아이템인지를 알려주는 부분으로 우리는 arraylist에 저장되있는 객체중 position에 해당하는것을 가져올것이므로
     * return null; ->return this.list_itemArrayList.get(position)으로 변경합니다.
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 이 부분이 리스트뷰에서 아이템과 xml을 연결하여 화면에 표시해주는
     * 가장 중요한 부분입니다. getView부분에서 반복문이 실행된다고 이해하시면 편하며 순차적으로 한칸씩 화면을 구성해주는 부분입니다.
     * (여기서 부터는 이해가 가지않으시면 패턴을 암기하시면됩니다.)
     * 우선 convertView라는 파라미터를 메소드에서 주는데요 이 부분에 우리가 만든 item.xml을 불러와야합니다. 여기는 엑티비티가 아니므로 불러오기위한 약간의 절차가 필요한데요 그 때문에 위에서 저희가 Context를 생성자를 통해 받은것입니다.
     * LayoutInflater 클래스를 이용하면 다른 클래스에서도 xml을 가져올수있는데요
     * @param position
     * @param convertView
     * @param parent
     * @return
     */

    TextView nickname;
    TextView date_textView;
    TextView content_textView;
    TextView comment;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item,null);
            nickname = (TextView)convertView.findViewById(R.id.nickname);
            content_textView = (TextView)convertView.findViewById(R.id.textView);
            date_textView = (TextView)convertView.findViewById(R.id.timestamp);
            comment = (TextView)convertView.findViewById(R.id.comment);

            nickname.setText(list_itemArrayList.get(position).getNickname());
            content_textView.setText(list_itemArrayList.get(position).getContent());
            date_textView.setText(list_itemArrayList.get(position).getWrite_date().toString());
            comment.setText(list_itemArrayList.get(position).getComment_count());

        }
        return convertView;
    }

    public void addItem(String nickname, String date, String content, int comment) {
        List_item item = new List_item();
        item.setNickname(nickname);
        item.setContent(content);
        item.setComment_count(comment);
        item.setWrite_date(date);

        list_itemArrayList.add(item);

    }
}
