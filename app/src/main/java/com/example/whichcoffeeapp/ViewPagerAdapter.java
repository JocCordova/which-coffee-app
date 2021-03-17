package com.example.whichcoffeeapp;

import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>{
    DatabaseHelper myDb;
    int holeFillerId=-1;
    int skips=0;
    int sk = 0;
    boolean skip = false;
    int sumTotal;

    int[] images;
    private int[] colorArray = new int[]{R.color.one, R.color.two, R.color.three ,R.color.four,R.color.five};



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view_pager, parent, false);
            myDb = new DatabaseHelper(view.getContext());
            int sum = countCoffee();
            if(sum==0) {
                view.setVisibility(View.INVISIBLE);
            }
        sumTotal = countCoffee();

            return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        int sum = countCoffee();





        if(sum!=0) {
            int pos = position % (sum );
            int count=0;
            int coffeeId=position;

            if(skip){
                pos = pos + skips;
            }
            //coffeeId = pos +1;
            Toast.makeText(holder.coffeeChart.getContext(), "Position: "+position+"Pos: "+pos+"skips: "+skips, Toast.LENGTH_SHORT).show();
            while(!myDb.coffeeExists(coffeeId)&& pos < sum){
                skips++;
                skip=true;
                pos++;
            }
            if(!myDb.coffeeExists(coffeeId)&& pos == sum){pos=0;}

            int colorPos = position % colorArray.length;

            String[] names = getTitles(viewLog(pos+1));

            holder.coffeeName.setText(names[0]);
            holder.coffeeOrigin.setText(names[1]);
            holder.coffeeProcess.setText(names[2]);
            holder.roastDate.setText(names[3]);

            holder.coffeeName.setBackgroundResource(colorArray[colorPos]);

            int finalPos = pos + 1;
            holder.coffeeName.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openCoffee(v, (finalPos));
                }
            });
            holder.coffeeChart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    openCoffee(v, (finalPos));
                }
            });
        }
    }
    public Cursor viewLog(int id) {
        Cursor res = myDb.getCoffeeById(id);
        res.moveToFirst();
        return res;
    }
    public int countCoffee() {
        Cursor res;
        try{res = myDb.countCoffee();
        }catch(Exception e){return 0;}


        if( res != null && res.moveToFirst() ) {
            int sum = Integer.parseInt(res.getString(0));
            res.close();
            return sum;
        }
        return 0;
    }

    public String[] getTitles(Cursor res) {
        String cName="",cOrigin="",cProcess="",cRoastDate="";

        if( res != null && res.moveToFirst() ){
            cName = res.getString(0);
            cOrigin = res.getString(1);
            cProcess = res.getString(2);
            cRoastDate = res.getString(3);
        }
        res.close();
        String [] names = {cName,cOrigin,cProcess,cRoastDate};
        return names;
    }

    public void openCoffee(View v,int id){
        Intent intent = new Intent(v.getContext(), OpenCoffee.class);
        intent.putExtra("COFFEE_ID", String.valueOf(id));
        v.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {

        return (Integer.MAX_VALUE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView coffeeName,coffeeChart,coffeeOrigin,coffeeProcess,roastDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeName = itemView.findViewById(R.id.coffeeName);
            coffeeChart = itemView.findViewById(R.id.coffeeChart);
            coffeeOrigin = itemView.findViewById(R.id.coffeeOrigin);
            coffeeProcess = itemView.findViewById(R.id.coffeeProcess);
            roastDate = itemView.findViewById(R.id.roastDate);


        }
    }

}
