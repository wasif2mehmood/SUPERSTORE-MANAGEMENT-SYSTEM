import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * This class provides a date formatter for use in Swing applications.
 * It extends the AbstractFormatter class and formats dates according to the specified date pattern.
 */
public class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "yyyy/MM/dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    /**
     * Converts a string representation of a date into a Date object.
     *
     * @param text The string representation of a date.
     * @return A Date object representing the parsed date.
     * @throws ParseException if the parsing fails.
     */
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    /**
     * Converts a Date object into a string representation of the date.
     *
     * @param value The Date object to be formatted.
     * @return A string representation of the formatted date.
     * @throws ParseException if the formatting fails.
     */
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}
