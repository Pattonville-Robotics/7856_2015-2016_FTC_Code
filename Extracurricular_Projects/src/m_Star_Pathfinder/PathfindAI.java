package m_Star_Pathfinder;

import java.awt.Point;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PathfindAI
{
	private PathfindAI()
	{
	}

	public static void computeDistance(Grid grid, Point start)
	{
		List<Point> points = new ArrayList<Point>(grid.getGrid().length * 4);
		AbstractSet<Point> pointSet = new HashSet<Point>((int) Math.pow(grid.getGrid().length, 2));
		points.add(start);
		boolean noMoreLeft = false;
		int distance = 0;
		while (!noMoreLeft)
		{
			for (int i = 0; i < points.size(); i++)
			{
				// System.out.println("Setting distance of: (" + points.get(i).x
				// + ", " + points.get(i).y + ")");
				grid.setSquareDistance(points.get(i), distance);
			}

			List<Point> newPoints = new ArrayList<Point>();
			int numFailedCells = 0;

			final int[] xMod = new int[] { 1, -1, 0, 0 };
			final int[] yMod = new int[] { 0, 0, 1, -1 };

			for (int i = 0; i < points.size(); i++)
			{
				// System.out.println("Point " + i + " (" + points.get(i).x +
				// ", " + points.get(i).y + ")");
				int numFailed = 0;
				for (int j = 0; j < xMod.length; j++)
				{
					Point prospectivePoint = new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j]);
					if (grid.canAccess(prospectivePoint)
							&& grid.getSquareCopy(prospectivePoint).getDistance() == -1
							&& (grid.getSquareCopy(prospectivePoint).getContents() == SquareType.EMPTY || grid.getSquareCopy(prospectivePoint).getContents() == SquareType.FINISH))
					// If it's not outside the grid and hasn't been set already
					// and is an empty square
					{
						if (!pointSet.contains(new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j])))
						{
							newPoints.add(new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j]));
							pointSet.add(new Point(points.get(i).x + xMod[j], points.get(i).y + yMod[j]));
						}
						else
						{
							// System.out.println("Tried to add a point twice!");
						}
						// Add it to the new list
					}
					else
					{
						numFailed++;
					}
				}

				if (numFailed == 4) // If all four sides failed to be set
				{
					numFailedCells++; // This cell completely failed
				}
			}
			// System.out.println(distance);
			distance++;
			/*
			 * System.out.println(Arrays.toString(points.toArray()));
			 * System.out.println(Arrays.toString(newPoints.toArray()));
			 */
			if (numFailedCells == points.size() || points.size() == 0)
			// If all cells completely failed
			{
				noMoreLeft = true; // End looping
				grid.setFurthestPoint(points.get(0));
			}
			points.clear();
			points.addAll(newPoints);

		}
	}

	public static void computePaths(Grid grid)
	{
		ArrayList<VertexMap> paths = new ArrayList<VertexMap>();
		paths.add(new VertexMap());
		paths.get(0).addPoint(grid.getFinishPoint());
		// System.out.println(paths);

		boolean allZero = false;
		int progressFromFinish = 0;
		while (!allZero)
		{
			for (int i = 0; i < paths.size(); i++)
			{
				System.out.println("i = " + i);
				System.out.println("lowestAdjacentSquares = " + grid.getLowestAdjacentSquares(paths.get(i).getPoint(progressFromFinish)));
				ArrayList<Point> adjacent = grid.getLowestAdjacentSquares(paths.get(i).getPoint(progressFromFinish));

				if (adjacent.size() == 1)
				{
					System.out.println(paths.get(i) + " gets point " + adjacent.get(0) + "added.");
					paths.get(i).addPoint(adjacent.get(0));
				}
				else if (adjacent.size() == 2)
				{
					VertexMap altMap = paths.get(i).clone();

					System.out.println("altMap = " + altMap);
					
					paths.get(i).addPoint(adjacent.get(1));
					altMap.addPoint(adjacent.get(1));

					paths.add(altMap);
					i++;
				}
				else if (adjacent.size() == 0)
				{
					allZero = true;
					System.out.println("Found zero adjacent lower squares");
				}
				else
				{
					allZero = true;
					System.out.println("Less than 0 or more than 2 adjacent points were returned! This is a sign of something bad!");
				}
			}
			progressFromFinish++;
			// System.out.println(progressFromFinish);
		}
		grid.setPaths(paths);
	}
}