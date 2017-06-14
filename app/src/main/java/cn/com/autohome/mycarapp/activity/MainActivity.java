package cn.com.autohome.mycarapp.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.autohome.mycarapp.R;
import cn.com.autohome.mycarapp.utils.NetUtil;
import cn.com.autohome.mycarapp.view.ProgressArcView;
import cn.com.autohome.mycarapp.view.SuperTextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressArcView processArc;
    private SuperTextView stv1;
    private Path firstLinePath = new Path();
    private float totalWidth = 50f;
    private PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    private float density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        density = getResources().getDisplayMetrics().density;
        setContentView(R.layout.activity_main);
        TextView bt1 = (TextView) findViewById(R.id.bt1);
        TextView bt2 = (TextView) findViewById(R.id.bt2);
        processArc = (ProgressArcView) findViewById(R.id.processArc);
        stv1 = (SuperTextView) findViewById(R.id.stv1);

        initView();

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        checkNet();
    }

    private void initView() {
        processArc.setProcess(0.48f);
        processArc.setColor(Color.parseColor("#4183ff"));
        processArc.setTitleText("我的口碑");
        processArc.startAnimation();

        stv1.setOnAminListener(new SuperTextView.OnAminListener(){

            @Override
            protected void onDrawAnim(SuperTextView v, Canvas canvas, float rate) {
                drawMoveLine(canvas,v,rate);
            }

            @Override
            protected void onAnimationUpdate(SuperTextView v, float rate) {

            }
        });
        stv1.startAnimation();
    }

    private void checkNet() {
        if(!NetUtil.CheckNetState()){
            Toast.makeText(this,"无网络", Toast.LENGTH_SHORT).show();
        }else{
            if(NetUtil.isWifi())
                Toast.makeText(this,"WIFI",Toast.LENGTH_SHORT).show();
            if(NetUtil.isMobile())
                Toast.makeText(this,"手机网络",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.bt1:
                intent = new Intent(this,ViewTouchActivity.class);
                startActivity(intent);
                break;
            case R.id.bt2:
                intent = new Intent(this,ViewGroupTouchActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void drawMoveLine(Canvas canvas, View v, float rate) {
//        private Path secondLinePath = new Path();
        int width = v.getWidth();
        int height = v.getHeight();
        float currentLocation = rate * width;
        int firstLineWidth = (int) (totalWidth * density / 5 * 3);
        firstLinePath.reset();
        firstLinePath.moveTo(currentLocation, height);
        firstLinePath.lineTo(currentLocation + firstLineWidth, height);
        firstLinePath.lineTo((float) (currentLocation + firstLineWidth + height * Math.tan(60)), 0);
        firstLinePath.lineTo((float) (currentLocation + height * Math.tan(60)), 0);
        firstLinePath.close();
        Paint paint = new Paint();
        paint.setXfermode(xfermode);
        paint.setColor(Color.parseColor("#4dffffff"));
        canvas.drawPath(firstLinePath, paint);
    }

}
