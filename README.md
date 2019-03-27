# RecylerView
RecyclerView, ListView'den daha birçok özelliğe sahip olduğu için ListView'in gelişmiş bir sürümüdür. Şu an itibariyle, RecyclerView, ListView'a göre daha çok tercih edilmektedir.

Custom Adapter ile RecyclerView Uygulamak için yapılması gereken adımlar:

-RecyclerView projemize eklemek için build.gradle dosyasına bunu ekliyoruz ve Sync Now’a tıklıyoruz.
implementation 'com.android.support:recyclerview-v7:28.0.0'

Şimdi de activity_main.xml ‘e recyclerview componentini ekleyelim

activity_main.xml :
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">
    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycleViewContainer"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true" />
    
    
</RelativeLayout>
Sonraki adım,RecyclerView de tek bir sıranın nasıl görüneceğini biçimlendirmek için özel bir tasarım dosyası oluşturmaktır.
Res→layout→new→layout resourse file dedikten sonra single_list_item diye adlandırıyoruz.

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    <RelativeLayout
        android:id="@+id/singleRow"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="8dp">
        <ImageView
            android:id="@+id/userImg"
            android:src="@mipmap/ic_launcher"
            android:layout_width="60dp"
            android:layout_height="60dp" />
        <TextView
            android:id="@+id/pNametxt"
            android:text="User Name"
            android:textSize="20sp"
            android:layout_marginTop="6dp"
            android:maxLines="1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentTop="true"
            android:layout_toRightOf="@+id/userImg"
            android:layout_toEndOf="@+id/userImg"
            android:layout_marginLeft="8dp"
            android:layout_marginStart="8dp" />
        <TextView
            android:id="@+id/pJobProfiletxt"
            android:text="Job Profile"
            android:textSize="14sp"
            android:maxLines="1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_below="@+id/pNametxt"
            android:layout_alignLeft="@+id/pNametxt"
            android:layout_alignStart="@+id/pNametxt" />
    </RelativeLayout>
    <View
        android:layout_below="@+id/singleRow"
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="#f2f2f2" />
</RelativeLayout>

Şimdi alınan verileri nesnelerde depolamak için bir model sınıfı oluşturmamız gerekiyor bunun içinde proje klasörüne sağ tıklayıp new→javaclass 
PersonelUtils.java
package com.example.recylerview;
public class PersonUtils {
    private String personName;
    private String jobProfile;
    public PersonUtils(String personName, String jobProfile) {
        this.personName = personName;
        this.jobProfile = jobProfile;
    }
    public String getPersonName() {
        return personName;
    }
    public void setPersonName(String personName) {
        this.personName = personName;
    }
    public String getJobProfile() {
        return jobProfile;
    }
    public void setJobProfile(String jobProfile) {
        this.jobProfile = jobProfile;
    }
}
Artık verileri RecyclerView içine yerleştirmek için bir adaptöre ihtiyacımız var.Proje klasörümüze sağ tıklayıp new→javaclass’a tıklıyoruz ve CustomRecyclerAdapter olarak adlandırın ve RecyclerView.Adapter ile extend edin.

package com.example.recylerview;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<PersonUtils> personUtils;
    public CustomRecyclerAdapter(Context context, List personUtils) {
        this.context = context;
        this.personUtils = personUtils;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(personUtils.get(position));
        PersonUtils pu = personUtils.get(position);
        holder.pName.setText(pu.getPersonName());
        holder.pJobProfile.setText(pu.getJobProfile());
    }
    @Override
    public int getItemCount() {
        return personUtils.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView pName;
        public TextView pJobProfile;
        public ViewHolder(View itemView) {
            super(itemView);
            pName = (TextView) itemView.findViewById(R.id.pNametxt);
            pJobProfile = (TextView) itemView.findViewById(R.id.pJobProfiletxt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PersonUtils cpu = (PersonUtils) view.getTag();
                    Toast.makeText(view.getContext(), cpu.getPersonName()+" is "+ cpu.getJobProfile(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
Ardından ArrayList’e veri eklemek ve bunu MainActivity’de adapter kullanarak RecyclerView içine yerleştirmek için son bir adımımız kalıyor.
package com.example.recylerview;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<PersonUtils> personUtilsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        personUtilsList = new ArrayList<>();
        personUtilsList.add(new PersonUtils("Sevde Aybüke Kaleli","Project Manager"));
        personUtilsList.add(new PersonUtils("Ayşe Gün","Senior Developer"));
        personUtilsList.add(new PersonUtils("Ahmet Sezer","Lead Developer"));
        personUtilsList.add(new PersonUtils("Can Caner","Lead Developer"));
        personUtilsList.add(new PersonUtils("Hilal Al","UI/UX Developer"));
        personUtilsList.add(new PersonUtils("Begüm Günel","Front-End Developer"));
        personUtilsList.add(new PersonUtils("Damla Yanar","Backend Developer"));
        personUtilsList.add(new PersonUtils("Merve Su","Android Developer"));
        mAdapter = new CustomRecyclerAdapter(this, personUtilsList);
        recyclerView.setAdapter(mAdapter);
    }
}





