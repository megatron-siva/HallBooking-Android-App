package com.nandha.hallbooking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ExampleAdapterBookings extends RecyclerView.Adapter<ExampleAdapterBookings.ExampleViewHolder> {
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView,mImageView1,mImageView2,mImageView3,mImageView4;
        public TextView mTextView1;
        public TextView mTextView2;
        public ImageView mDeleteImage;
        public static ImageView accept;
        public ImageView reject;
        public TextView accepttext;
        public TextView rejecttext;
        public TextView CollegeName;
        public TextView HallName;
        public TextView NatureOfFunction;
        public TextView Date;
        public TextView Timing;
        public  TextView Requirements;
        public TextView mobileno;



        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            final OnItemClickListener lis = listener;
            mImageView = itemView.findViewById(R.id.imageview);
            mTextView1 = itemView.findViewById(R.id.TextView);
            mTextView2 = itemView.findViewById(R.id.TextView2);
            // mDeleteImage = itemView.findViewById(R.id.imageview1);
            accept = itemView.findViewById(R.id.imageview4);
            reject = itemView.findViewById(R.id.imageview5);
            accept.setVisibility(View.GONE);
            //accepttext = itemView.findViewById(R.id.textView8);
            //rejecttext = itemView.findViewById(R.id.textView9);
            CollegeName = itemView.findViewById(R.id.textView);
            HallName = itemView.findViewById(R.id.textView11);
            NatureOfFunction = itemView.findViewById(R.id.textView23);
            Date = itemView.findViewById(R.id.textView4);
            Timing = itemView.findViewById(R.id.textView5);
            Requirements = itemView.findViewById(R.id.textView6);
            mobileno = itemView.findViewById(R.id.textView14);
            mImageView1=itemView.findViewById(R.id.imageView);
            mImageView2=itemView.findViewById(R.id.imageView2);
            mImageView3=itemView.findViewById(R.id.imageView3);
            mImageView4=itemView.findViewById(R.id.imageView4);




            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

           /* mDeleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });*/
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Bookings.getInstance().AcceptMethod(String.valueOf(position), listener);
                            //if(b)
                            //listener.onDeleteClick(position);

                        }
                    }


                }
            });
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Bookings.getInstance().RejectMethod(String.valueOf(position),listener);
                        }
                    }
                }
            });

        }}


    public ExampleAdapterBookings(ArrayList<ExampleItem> exampleList) {
        mExampleList = exampleList;

    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);

        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
        holder.CollegeName.setText(currentItem.getText3());
        holder.HallName.setText(currentItem.getText4());
        holder.NatureOfFunction.setText(currentItem.getText5());
        holder.Date.setText(currentItem.getText6());
        holder.Timing.setText(currentItem.getText7());
        holder.Requirements.setText(currentItem.getText8());
        holder.mobileno.setText(currentItem.getText9());
        //holder.mImageView1.setImageResource(R.drawable.green_accept);
       // holder.mImageView3.setImageResource(R.drawable.green_accept);
        //holder.mImageView4.setImageResource(R.drawable.green_accept);
        String s=currentItem.getText10();
        String[] array;
        String[] array0=s.split("");
        if(array0.length==s.length()){
            array = new String [array0.length+1];
            array[0]=null;
            for(int i=0;i<array0.length;i++){
                array[i+1]=array0[i];
            }
        }
        else {
            array=s.split("");
        }


        if(s.equals("booked")){
            holder.mImageView1.setImageResource(R.drawable.green_booked);
            holder.mImageView2.setVisibility(View.INVISIBLE);
            holder.mImageView3.setVisibility(View.INVISIBLE);
            holder.mImageView4.setVisibility(View.INVISIBLE);
        }
        else {
            holder.mImageView1.setVisibility(View.INVISIBLE);
            holder.mImageView2.setVisibility(View.INVISIBLE);
            holder.mImageView3.setVisibility(View.INVISIBLE);
            holder.mImageView4.setVisibility(View.INVISIBLE);

            for(int i=1;i<array.length;i++){
                if(i==1){
                    if(array[i].equals("1")){
                    holder.mImageView1.setImageResource(R.drawable.green_accept);
                    holder.mImageView1.setVisibility(View.VISIBLE);}
                    else {
                        holder.mImageView1.setImageResource(R.drawable.red_reject);
                        //holder.mImageView1.setRotation(270);
                        holder.mImageView1.setVisibility(View.VISIBLE);
                    }
                }
                if(i==2) {
                    if (array[i].equals("2")) {
                        holder.mImageView2.setImageResource(R.drawable.green_accept);
                        holder.mImageView2.setVisibility(View.VISIBLE);
                    } else {
                        holder.mImageView2.setImageResource(R.drawable.red_reject);
                        //holder.mImageView2.setRotation(270);
                        holder.mImageView2.setVisibility(View.VISIBLE);
                    }
                }
                if(i==3) {
                    if (array[i].equals("3")) {
                        holder.mImageView3.setImageResource(R.drawable.green_accept);
                        holder.mImageView3.setVisibility(View.VISIBLE);
                    } else {
                        holder.mImageView3.setImageResource(R.drawable.red_reject);
                        //holder.mImageView3.setRotation(270);
                        holder.mImageView3.setVisibility(View.VISIBLE);
                    }
                }
                if(i==4){
                    if (array[i].equals("4")){
                        holder.mImageView4.setImageResource(R.drawable.green_accept);
                        holder.mImageView4.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.mImageView4.setImageResource(R.drawable.red_reject);
                        //holder.mImageView4.setRotation(270);
                        holder.mImageView4.setVisibility(View.VISIBLE);
                    }

                }
            }
        }
    }



    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}