/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supportClasses;

public class CalcUtil
{

	public static double getRangeRandomValue(double value)
	{
		return (-value + 2 * Math.random() * value);
	}

	public static double getRandomValueWithinRange(double valueFrom, double valueTo)
	{
		return valueFrom + (valueTo - valueFrom) * Math.random();
	}

	public static double getDistributionRandomValue(double valueFrom, double valueTo)
	{
		return getRangeRandomValue(getRandomValueWithinRange(valueFrom, valueTo));
	}

	public static String formatNum(double number)
	{
		String result = "" + number;

		if (result.indexOf("E") != -1)
		{
			try
			{
				String numerPart = result.substring(0, result.indexOf("E"));
				String ePart = result.substring(result.indexOf("E"));

				double cutNumber = Double.parseDouble(numerPart);

				cutNumber = (double) (Math.round(cutNumber * 100d)) / 100d;

				result = cutNumber + ePart;
				return result;
			} catch (NumberFormatException e)
			{
				return result;
			}
		} else
			result = "" + (double) (Math.round(number * 100d)) / 100d;

		return result;
	}

	public static double getGoodViewScaleValue(double scale)
	{
		String textFormat = scale + "";

		if (textFormat.indexOf("E") != -1)
		{
			try
			{
				String numerPart = textFormat.substring(0, textFormat.indexOf("E"));
				String ePart = textFormat.substring(textFormat.indexOf("E"));

				double cutNumber = Double.parseDouble(numerPart);

				cutNumber = (double) (Math.round(cutNumber));

				textFormat = cutNumber + ePart;
				return Double.parseDouble(textFormat);
			} catch (NumberFormatException e)
			{
				return scale;
			}
		}

		return scale;
	}
}
