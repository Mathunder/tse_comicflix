package app.ui.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import app.helpers.ComicVineSearchFilter;
import app.services.ComicVineService;


@SuppressWarnings("serial")
public class FilterComBox extends JPanel{
	public FilterComBox(ComicVineService comicVineService) {
	

		String[] values = new String[] { "Issue", "Character" };
		final JComboBox<String> comboBox = new JComboBox<String>(values) {
			@Override
			public void setPopupVisible(boolean visible) {
				if (visible) {
					super.setPopupVisible(visible);
				}
			}
		};
		this.setLayout(null);
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
		renderer.setSelected("Issue");
		comboBox.addItemListener(e -> {
			String item = (String) e.getItem();			
			if ( e.getStateChange() != ItemEvent.DESELECTED ) {
				renderer.setSelected(item);
				switch (item) {
				case "Issue" :{
					comicVineService.updateFilter(ComicVineSearchFilter.ISSUE);
					break;
				}
				case "Character" :{
					comicVineService.updateFilter(ComicVineSearchFilter.CHARACTER);
					break;
				}
				}
			}
		});
		comboBox.setBounds(0,0,175,55);
		this.add(comboBox);
	}

}
