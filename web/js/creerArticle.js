/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

jQuery(document).ready(function($){
    // Ce code est appelée quand la page est chargée ou reloadée
    // On charge les 5 derniers articles (ou moins) depuis le web service
    
    // On cache le bouton de sauvegarde des modifications pour chaque article
    $("#update-article").hide();

    // URL du WS et fonction de callback en cas de succ-s
    $.get("/Blog-Kraria-Sweyllam/resources/articles/0/5",function(data){
        
        var i = 0;
        // no article ?
        if(data != null) {
            // The json returned by the WebService looks like this: (use JS debug console
            // in your browser !
            // CAS 1: un seul article: 
            // {"article":{"content":"Voici une photo de Rome cet été...",
            //             "id":"2","time":"2012-12-19T16:30:47.612+01:00",
            //             "titre":"Photos de vacances"}
            // }
            // CAS 2 : plusieurs articles
            // {"article":[
            //      {"content":"Voici une photo de Rome cet été...",
            //       "id":"2","time":"2012-12-19T16:30:47.612+01:00",
            //       "titre":"Photos de vacances"},
            //      {"content":"Second contenu",
            //       "id":"3","time":"2012-12-19T16:32:25.717+01:00",
            //       "titre":"Second titre"}
            //       ]}
            // Quand il y en a plusieurs, on a un tableau [...]
            // Mais la méthode each de jQuery traite indifféremment les deux cas. 
            // merci jQuery.
            $(data.article).each(function(){
                i++
                $("#list-article").prepend(renderItem(this.id, this.titre, this.content, this.time));
            });
            if(i==0)
                showWelcome();
        
            // Si on est venu ici après une suppression on supprime le lien
            // "load more"
            if(removeLoadMore()){
                $("#loadmore").remove();
                console.log("DELETE");
            }else
            {
                // Si on vient ici après la publication d'un nouvel article on
                // fait apparitre le bouton "load more"
                console.log("INSERT");
                $("<div id='loadmore'><a href='#' id='load' >J'en veux plus !</a></div>").insertAfter("#list-article");
            }
        }
    },"json");
    
    
    // Si on clique sur le bouton "publier". L'utilisation de live() permet
    // de binder des events sur des éléments qui n'existent peut être pas encore
    $("#write").live('click',function(){
        // On récupère le contenu du formulaire en JSON
        var data = $("#form-article").serializeArray();
        // On fait un POST sur le web service d'insertion
        $.post("/Blog-Kraria-Sweyllam/resources/articles",data,function(d){
            $("#form-article").each(function(){
                this.reset();
            });
            // On ajoute l'article dans la page
            $.get(d,function(data){
                $("#list-article").prepend(renderItem(data.id, data.titre, data.content, data.time));  
            });
        });
       
        if(removeLoadMore()){
            $("#loadmore").remove();
        }

    });


    // Clic sur le bouton delete pour supprimer un article
    $(".delete").live("click",function(){

        var id = $(this).attr("href");
        console.log(id);

        $.ajax(id,
        {
            type:"DELETE",
            success: function(d){
                $("#article-"+d).slideUp('slow',function(){
                    $(this).remove()
                    });
            }
        });
        
        if(removeLoadMore()){
            $("#loadmore").remove();
        }
        
        return false;

    });

    // Click sur un titre d'article
    $(".title").live("click",function(){
        var url = $(this).attr("href");

        $(document).scrollTop($(document).height());

        $.get(url,function(data){

            $("#formupdate-article #titre").val(data.titre);
            $("#formupdate-article #content").val(data.content);
            $("#formupdate-article #id").val(data.id);

            $("#write-article").hide();
            $("#update-article").show();
        });
        
        if(removeLoadMore()){
            $("#loadmore").remove();
        }

        return false;
    });

    // Clic sur le bouton update pour modifier un article
    $("#update").live("click",function(){

        var data = $("#formupdate-article").serializeArray();
        console.log(data);
        
        $.ajax({
            url: "/Blog-Kraria-Sweyllam/resources/articles",
            type:"PUT",
            data: data,
            success: function(d){
                $("#formupdate-article").each(function(){
                    this.reset();
                });

                updateRenderedItem(d.id, d.titre, d.content, d.time)

                $("#update-article").hide();
                $("#write-article").show();

            }

        });

        if(removeLoadMore()){
            $("#loadmore").remove();
        }

    });

    // clic sur le lien "load more"
    $("#load").live("click",function(){
        $("#loadmore").remove();
        var count = $("#list-article").children().length;
        var limit = count+5;

        $.get("/Blog-Kraria-Sweyllam/resources/articles/"+count+"/"+limit,function(data){
          

            $(data).each(function(){
               
                $("#list-article").append(renderItem(this.id, this.titre, this.content, this.time));
            });
            if(removeLoadMore()){
                $("#loadmore").remove();
            }else{
                $("<div id='loadmore'><a href='#' id='load' >J'en veux plus !</a></div>").insertAfter("#list-article");
            }
        },"json");
    });
    

    function removeLoadMore()
    {
        $.get("/Blog-Kraria-Sweyllam/resources/articles/count",function(data){
            var i = $("#list-article").children().length;
            console.log("dans la bd : "+data+" | sur le site : "+i);
            if(data == i){
                return true;
            }else
                return false;
        });
    }

    // creation et ajout d'un article dans la page
    function renderItem(id, titre, content, date)
    {
        var myDate = new Date( date );
        var strDate = "";
        strDate += myDate.getUTCDate()+"/"+myDate.getMonth()+"/"+myDate.getFullYear();
        strDate += " à "+myDate.getHours()+":"+myDate.getMinutes();
        return "<div class='article' id='article-"+id+"'>\
                <h2>"+titre+"</h2></a>\
                <p class='content'>"+content+"</p>\
                <div class='postmeta'>\n\
                    <p class='alignleft'>Article publi&eacute; le "+strDate+"</p>\n\
                    <p class='alignright'>\n\
                        <a class='button blue delete' href='/Blog-Kraria-Sweyllam/resources/articles/"+id+"'>Supprimer</a>\n\
                        <a href='/Blog-Kraria-Sweyllam/resources/articles/"+id+"' class='button blue title'>Modifier</a>\
                    </p></div>\n\
                    <div class='clearfix'></div>\
                </div>";
    }

    function updateRenderedItem(id, titre, content)
    {
        console.log(id);

        $("#article-"+id+" h2").html(titre);
        $("#article-"+id+" .title").attr("rel","/Blog-Kraria-Sweyllam/resources/articles/"+id);
        $("#article-"+id+" .content").html(content);

        $("#article-"+id).css("background-color","#E3F6CE");
        window.setTimeout(function() {  
            $("#article-"+id).css("background-color","white");
        }, 1000); 

    }
    
    function showWelcome(){
        $("#list-article").html("<div id='welcome'>Aucun article n'est présent.<br />Voulez-vous &ecirc;tre le premier ?</div>");
    }

});