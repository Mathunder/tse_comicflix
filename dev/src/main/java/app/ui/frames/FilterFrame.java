package app.ui.frames;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

public class FilterFrame extends JFrame {

	public FilterFrame() {
		begin();
	}

	private void begin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();

		String[] values = new String[] { "Issue", "Character" };
		final JComboBox<String> comboBox = new JComboBox<String>(values) {
			@Override
			public void setPopupVisible(boolean visible) {
				if (visible) {
					super.setPopupVisible(visible);
				}
			}
		};

		class CheckBoxRenderer implements ListCellRenderer<Object> {
			private Map<String, JCheckBox> items = new HashMap<>();

			public CheckBoxRenderer(String[] items) {
				for (String item : items) {
					JCheckBox box = new JCheckBox(item);
					this.items.put(item, box);
				}

			}

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				if (items.containsKey(value)) {
					return items.get(value);
				} else {
					return null;
				}
			}

			public void setSelected(String item) {
				if (item.contains(item)) {
					JCheckBox cb = items.get(item);
					
					cb.setSelected(!cb.isSelected());
				}
			}

		}

		final CheckBoxRenderer renderer = new CheckBoxRenderer(values);

		comboBox.setRenderer(renderer);
		comboBox.addItemListener(e -> {
			String item = (String) e.getItem();
			if (e.getStateChange() != ItemEvent.DESELECTED) {
				renderer.setSelected(item);
			}
		});
		panel.add(comboBox);
		getContentPane().add(panel);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				FilterFrame frame = new FilterFrame();

			}

		});
	}
}