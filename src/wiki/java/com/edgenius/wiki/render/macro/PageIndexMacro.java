/* 
 * =============================================================
 * Copyright (C) 2007-2011 Edgenius (http://www.edgenius.com)
 * =============================================================
 * License Information: http://www.edgenius.com/licensing/edgenius/2.0/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2.0
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * http://www.gnu.org/licenses/gpl.txt
 *  
 * ****************************************************************
 */
package com.edgenius.wiki.render.macro;

import java.util.ListIterator;

import com.edgenius.wiki.gwt.client.html.HTMLNode;
import com.edgenius.wiki.render.MacroParameter;
import com.edgenius.wiki.render.MalformedMacroException;
import com.edgenius.wiki.render.RenderContext;
import com.edgenius.wiki.render.handler.PageIndexHandler;
import com.edgenius.wiki.render.object.ObjectPosition;

/**
 * Macro {index:filter=*visible*|filterOut=*invisible*}
 * Display current space page index.
 * 
 * @author Dapeng.Ni
 */
public class PageIndexMacro extends BaseMacro {
	
	private final static String HANDLER = PageIndexHandler.class.getName();
	
	//JDK1.6 @Override
	public String[] getName() {
		return new String[]{"index"};
	}
	@Override
	public String getHTMLIdentifier() {
		return "<div aid='pageindex'>";
	}
	//JDK1.6 @Override
	public void execute(StringBuffer buffer, MacroParameter params) throws MalformedMacroException {
	
		RenderContext context = params.getRenderContext();
		ObjectPosition obj = new ObjectPosition(params.getStartMarkup());
		obj.uuid = context.createUniqueKey(true);
		obj.serverHandler = HANDLER;
		obj.values.putAll(params.getParams());
		
		context.getObjectList().add(obj);
		
		buffer.append(obj.uuid);
	}
	public boolean isPaired(){
		return false;
	}
	
	@Override
	protected void replaceHTML(HTMLNode node, ListIterator<HTMLNode> iter, RenderContext context) {
		resetMacroMarkup(TIDY_STYLE_BLOCK, node, iter, getMacroMarkupString(node, "index"), null);
		
		//reset all inside node to blank
		//!!! Here changes ListIterator cursor position!!!
		HTMLNode subnode;
		for(;iter.hasNext();){
			subnode = iter.next();
			if(subnode == node.getPair())
				break;
			
			if(!subnode.isTextNode()){
				subnode.reset("", true);
				if(subnode.getPair() != null)
					subnode.getPair().reset("", true);
			}else{
				subnode.reset("", true);
			}
		}
	}

}
