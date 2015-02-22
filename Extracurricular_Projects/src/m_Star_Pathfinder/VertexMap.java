package m_Star_Pathfinder;

import java.awt.Point;
import java.util.LinkedList;

public class VertexMap implements Comparable<VertexMap>, Cloneable
{
	private LinkedList<Point>	points;

	public VertexMap()
	{
		this.points = new LinkedList<Point>();
	}

	public VertexMap(LinkedList<Point> points)
	{
		this.points = points;
	}

	public VertexMap clone()
	{
		LinkedList<Point> newList = new LinkedList<Point>();

		for (Point p : points)
		{
			newList.add(p);
		}

		return new VertexMap(newList);
	}

	public LinkedList<Point> getList()
	{
		return points;
	}

	public void addPoint(Point p)
	{
		points.add(p);
	}

	public Point getPoint(int i)
	{
		return points.get(i);
	}

	public double getTotalDistance()
	{
		double sum = 0;

		for (int i = 0; i < points.size() - 1; i++)
		{
			sum += getDistance(points.get(i), points.get(i + 1));
		}

		return sum;
	}

	public double getDistance(Point p1, Point p2)
	{
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}

	public int compareTo(VertexMap map)
	{
		if (this.getTotalDistance() > map.getTotalDistance())
			return 1;
		else if (this.getTotalDistance() < map.getTotalDistance())
			return -1;
		else
			return 0;
	}

	public String toString()
	{
		return points.toString();
	}
}