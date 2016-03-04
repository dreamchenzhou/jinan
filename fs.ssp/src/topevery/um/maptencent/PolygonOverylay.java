package topevery.um.maptencent;

import java.util.ArrayList;

import topevery.um.map.UmLocation;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;

import com.tencent.mapsdk.raster.model.GeoPoint;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.Overlay;

/**
 * 画区域(网格)
 * 
 * @author martin.zheng
 * 
 */
public class PolygonOverylay extends Overlay
{
	private MapView mMapView;
	ArrayList<GeoPoint> geoPoints = new ArrayList<GeoPoint>();

	public PolygonOverylay(MapView mapView)
	{
		this.mMapView = mapView;
	}

	public void drawPolygon2(ArrayList<UmLocation> locations, boolean invalidate)
	{
		if (locations != null)
		{
			ArrayList<GeoPoint> geoPoints = new ArrayList<GeoPoint>();

			for (UmLocation location : locations)
			{
				GeoPoint geoPoint = TencentMapAPI.getGeoPoint(location);
				geoPoints.add(geoPoint);
			}

			drawPolygon(geoPoints, invalidate);
		}
	}

	public void drawPolygon(ArrayList<GeoPoint> geoPoints, boolean invalidate)
	{
		this.geoPoints.clear();
		if (geoPoints != null)
		{
			this.geoPoints.addAll(geoPoints);
		}

		if (invalidate)
		{
			mMapView.invalidate();
		}
	}

	@Override
	public void draw(Canvas canvas, MapView mapView)
	{
		drawPolygon(canvas, mMapView);
	}

	private void drawPolygon(Canvas canvas, MapView mapView)
	{
		ArrayList<Point> points = new ArrayList<Point>();
		for (GeoPoint geoPoint : geoPoints)
		{
			Point point = mapView.getProjection().toPixels(geoPoint, null);
			points.add(point);
		}

		Path pathpolygon = new Path();

		Path fillpolygon = new Path();

		Point p0 = points.get(0);
		pathpolygon.moveTo(p0.x, p0.y);
		fillpolygon.moveTo(p0.x, p0.y);

		for (int i = 1; i < points.size(); i++)
		{
			Point pt = points.get(i);
			pathpolygon.lineTo(pt.x, pt.y);
			fillpolygon.lineTo(pt.x, pt.y);
		}

		pathpolygon.lineTo(p0.x, p0.y);
		fillpolygon.lineTo(p0.x, p0.y);

		// 边界
		Paint paintpolygon = new Paint();
		paintpolygon.setStyle(Style.STROKE);
		paintpolygon.setStrokeWidth(5);
		paintpolygon.setColor(Color.RED);
		paintpolygon.setAntiAlias(true);
		// paintpolygon.setStrokeCap(Cap.ROUND);
		// paintpolygon.setStrokeJoin(Join.ROUND);
		canvas.drawPath(pathpolygon, paintpolygon);

		// 填充
		Paint paintfillpolygon = new Paint();
		paintfillpolygon.setStyle(Style.FILL);
		paintfillpolygon.setStrokeWidth(10);
		paintfillpolygon.setColor(Color.argb(30, 0, 0, 255));
		paintfillpolygon.setAntiAlias(true);
		paintpolygon.setStrokeCap(Cap.ROUND);
		paintpolygon.setStrokeJoin(Join.ROUND);
		canvas.drawPath(fillpolygon, paintfillpolygon);
	}
}
