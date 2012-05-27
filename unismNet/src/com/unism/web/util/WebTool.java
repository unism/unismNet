package com.unism.web.util;

public class WebTool {

	public static PageIndex getPageIndex(int maxresult, int currentpage,
			long totalpage) {
		long startindex = 0;
		long endindex = 0;
		if (currentpage + maxresult / 2 < totalpage) {
			if (currentpage > maxresult / 2) {
				startindex = maxresult % 2 == 0 ? currentpage - maxresult / 2
						+ 1 : currentpage - maxresult / 2;
				endindex = currentpage + maxresult / 2;
			} else {
				startindex = 1;
				endindex = maxresult < totalpage ? maxresult : totalpage;
			}
		} else {
			startindex = totalpage - maxresult < 0 ? 1 : totalpage - maxresult
					+ 1;
			endindex = totalpage;
		}
		return new PageIndex(startindex, endindex);
	}

	public static PageIndex getPageIndex(long viewPageCount, int currentPage,
			long totalpage) {
		long startindex = currentPage
				- (viewPageCount % 2 == 0 ? viewPageCount / 2 - 1
						: viewPageCount / 2);
		long endindex = currentPage + viewPageCount / 2;
		if (startindex < 1) {
			startindex = 1;
			endindex = totalpage > viewPageCount ? viewPageCount : totalpage;
		}
		if (endindex > totalpage) {
			endindex = totalpage;
			startindex = totalpage - viewPageCount < 0 ? 1 : totalpage
					- viewPageCount + 1;
		}
		return new PageIndex(startindex, endindex);
	}
}
