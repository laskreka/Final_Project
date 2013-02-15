/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var lecture = "";
var id = -1;

function initialiseComments(lec) {
    lecture = lec;
    id = -1;
    setInterval('getComments()', 1500);
}

function postComment()
{
    var req = new XMLHttpRequest();
    str = encodeURIComponent(document.getElementById("str").value);
    username = document.getElementById("username").value;
    
    req.open('GET', 'http://'+location.host+'/final_project/comments?action=add&lecture='+lecture+'&username='+username+'&str='+str , false);
    req.send();
    
    document.getElementById("str").value = "";
}

function getComments(){
    var req = new XMLHttpRequest();
    var ret;
    var c;
    var intRegex = /^\d+$/;

    
    req.open('GET', 'http://'+location.host+'/final_project/comments?action=list&lecture='+lecture+'&id='+id , false);
    req.send();
    
    
    if (req.status == 200) {
        // var x = document.getElementById('body');
        ret = req.responseText.split("&");

        if ( ret!=null &&   ret.length > 0 && intRegex.test(ret[0]) ) {
            
            if ( ret[0] > id ) {
                id = ret[0];
                c  = document.getElementById('comments');
                c.innerHTML = c.innerHTML + ret[1];
                c.scrollTop = c.scrollHeight;
            }
        }
        
   }
       
}