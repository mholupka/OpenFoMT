package fomt.base.ui;

import java.util.LinkedList;
import java.util.List;

public class UIContainer extends UIElement {
	
	/* --- Instance Methods (Interface) --- */
	
	public void add(UIElement e) { listChildren.add(e); }
	public void remove(UIElement e) { listChildren.remove(e); }
	
	/* --- Instance Fields --- */
	
	private List<UIElement> listChildren = new LinkedList<>();
	
}
