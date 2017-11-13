package tools;

import javafx.scene.control.TextFormatter;

public class NumberTextFormatter extends TextFormatter<Object>
{
	public NumberTextFormatter()
	{
		super(c -> {
			if(c.getControlNewText().isEmpty())
			{
				return c;
			}

			if(c.getControlNewText().matches("[0-9]*"))
			{
				return c;
			}
			else
			{
				return null;
			}
		});
	}
}