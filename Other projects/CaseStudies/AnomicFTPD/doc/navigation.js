var appname  = "AnomicFTPD: a full-featured open-source/freeware FTP Server in Java";
var thismenu = new Array(
    "index","News","Platforms","License","Download",
    "Installation","Usage","Configuration","Features",
    "FAQ","Contact");
var mainmenu = new Array(
    "Anomic Home@http://www.anomic.de/index.html",
    "Products@http://www.anomic.de/Products/index.html",
    "Consulting@http://www.anomic.de/Consulting/index.html",
    "Contact@http://www.anomic.de/Contact/index.html",
    "Impressum@http://www.anomic.de/Impressum/index.html");
var root = "http://www.anomic.de/";

function headline() {
  document.writeln(
    "<table bgcolor=\"#000088\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">" +
    "<tr>" +
      "<td width=\"240\" height=\"48\"><a href=\"" + root + "\"><img border=\"0\" src=\"grafics/headline.gif\" align=\"top\"></a></td>" +
      "<td width=\"100%\">" +
	"<br><table bgcolor=\"#000088\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\"><tr><td height=\"4\"></td></tr><tr><td align=\"center\" valign=\"top\"><font size=\"4\" face=\"Helvetica, Arial\" color=\"#ffffff\">NETWORK&nbsp;APPLIANCE&nbsp;&amp; CONSULTING</font></td></tr></table></td>" +
    "</tr>" +
    "</table>"
  );
}

function filename() {
  var p = window.location.pathname;
  return p.substring(p.lastIndexOf("/") + 1);
}

function docname() {
  var f = filename()
  return f.substring(0, f.indexOf("."));
}

function lmenu() {
  document.writeln("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
  var dn = docname();
  var printname;
  for (var i = 0; i < thismenu.length; ++i) {
    document.writeln("<tr><td height=\"2\"></td></tr>"); 
    if (thismenu[i] == "index") printname = "About"; else printname = thismenu[i];
    if (dn == thismenu[i]) {
      document.writeln("<tr><td height=\"20\" class=\"yellow\" bgcolor=\"#555566\" valign=\"middle\">&nbsp;" + printname + "</td></tr>"); 
    } else {
      document.writeln("<tr><td height=\"20\" bgcolor=\"#888899\" valign=\"middle\">&nbsp;<a href=\"" + thismenu[i] + ".html\" class=\"white\">" + printname + "</b></td></tr>"); 
    }
  }
  document.writeln("</table>");
}

function rmenu() {
  var linkpath;
  var printname;
  var pos;
  document.writeln("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
  for (var i = 0; i < mainmenu.length; ++i) {
    pos = mainmenu[i].indexOf("@");
    linkpath = mainmenu[i].substring(pos + 1);
    printname = mainmenu[i].substring(0, pos);
    document.writeln("<tr><td height=\"2\"></td></tr>"); 
    document.writeln("<tr><td height=\"20\" bgcolor=\"#888899\" valign=\"middle\">&nbsp;<a href=\"" + linkpath + "\" class=\"white\">" + printname + "</b></td></tr>"); 
  }
  document.writeln("</table>");
}


function globalheader() {
  document.writeln("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\"><tr><td>");
  headline();
  document.writeln("</td></tr>" +
                   "<tr><td height=\"1\" bgcolor=\"#000000\"></td></tr>" +
		   "<tr><td height=\"20\" class=\"white\" bgcolor=\"#445599\" align=\"center\" valign=\"middle\">" + appname + "</td></tr>" +
		   "<tr><td>" +
		   "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">" +
		   "  <tr>" +
                   "  <td width=\"100\" valign=\"top\">");
  lmenu();
  document.writeln("  </td>" +
                   "  <td width=\"10\" valign=\"top\"></td>" +
		   "  <td valign=\"top\">" +
		   "  <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">" +
		   "  <tr><td height=\"10\"></td></tr><tr><td><br><br>");
}

function globalfooter() {
  document.writeln("  <br><br></td></tr></table></td>" + 
                   "  <td width=\"10\" valign=\"top\">" +
                   "  </td>" +
                   "  <td width=\"100\" valign=\"top\">");
  rmenu();
  document.writeln("  </td>" +
                   "  </tr></table>" +
                   "</td></tr></table>");
}
