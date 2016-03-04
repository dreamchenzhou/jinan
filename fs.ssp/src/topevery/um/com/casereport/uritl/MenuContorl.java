package topevery.um.com.casereport.uritl;

import java.util.ArrayList;
import java.util.HashMap;

import topevery.um.com.camera.CameraUtils;
import topevery.um.com.camera.OnCameraListener;
import topevery.um.com.casereport.report.CaseUtils;
import topevery.um.com.casereport.report.Casereport;
import topevery.um.jinan.manager.R;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MenuContorl
{
	private Casereport casereport;
	private ListView listview;

	public MenuContorl(Casereport caseReport)
	{
		this.casereport = caseReport;
	}

	public void showListView()
	{

		// listview = (ListView) casereport.findViewById(R.id.util_list);
		if (Casereport.menu == 0)
		{
			ArrayList<HashMap<String, Object>> listitem = new ArrayList<HashMap<String, Object>>();

			HashMap<String, Object> map0 = new HashMap<String, Object>();
			map0.put("item", "照  相");
			listitem.add(map0);

			HashMap<String, Object> map1 = new HashMap<String, Object>();
			map1.put("item", "附  件");
			listitem.add(map1);

			SimpleAdapter adapter = new SimpleAdapter(casereport, listitem, R.layout.uitl_text, new String[] { "item" }, new int[] { R.id.btn_menu });

			listview.setAdapter(adapter);

			listview.setOnItemClickListener(new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id)
					{

						switch (position)
						{
							case 0:
								setCamera();
								listview.setVisibility(View.GONE);
								break;

							case 1:
								Intent intent = new Intent();
								// intent.setClass(casereport,
								// CaseAttachInfo.class);
								casereport.startActivity(intent);
								listview.setVisibility(View.GONE);
								break;

						}
					}
				});
			listview.setVisibility(View.VISIBLE);
			Casereport.menu = 1;
		}
		else
		{
			listview.setVisibility(View.INVISIBLE);
			Casereport.menu = 0;
		}

	}

	public void setCamera()
	{
		CameraUtils.value.camera(casereport, new OnCameraListener()
			{
				@Override
				public void onCamera(String fileName)
				{
					// AttachInfo attachInfo = new AttachInfo();
					// attachInfo.uri = fileName;
					// CaseUtils.attachs.add(attachInfo);
					CaseUtils.files.add(fileName);
					Intent intent = new Intent();
					// Bundle bundle = new Bundle();
					// bundle.putString("fileName", fileName);
					// intent.putExtras(bundle);

					// intent.setClass(casereport, CaseAttachInfo.class);
					casereport.startActivity(intent);
				}
			});

	}

}
