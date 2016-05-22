<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: 15256
  Date: 12/02/2016
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="se-pre-con"></div>

<div id="conteneur" class="col-lg-12 col-md-12 ">
    <div id="ajoutez" class="col-lg-12 col-md-12 page-para">
        <div id="seconnecterindex" class="col-xs-9 col-xs-offset-2">
            <form:form action="/user/login" method="post" modelAttribute="loginAttribute">


                <h2>Se connecter</h2>
                <div class="form-group col-md-5"><form:input type="text" class="form-control" path="username"
                                                             placeholder="login"/></div>
                <div class="form-group col-md-5"><form:input type="password" onfocus="this.value=''"
                                                             class="form-control" path="password"
                                                             placeholder="mot de passe"/></div>

                <div class="form-group col-md-2">
                    <button type="submit" class="btn btn-success">Connexion</button>
                </div>
                <hr class="col-xs-12">
                <h3>Ou s'incrire/se connecter via:</h3>
                <div class="form-group">
                    <img type="subtmit" value="Facebook login" id="fbLogin" onclick="" class="login"
                         src="/resources/img/bigFbLogin.png">
                    <img type="submit" value="Google login" id="googlelogin" class="login"
                         src="/resources/img/bigGoogleLogin.png">
                </div>

                <hr/>
                <div><a href="<spring:url value='/user/add'/>"> Créer un nouveau compte deadpineapple </a></div>


            </form:form>


        </div>

        <div class="scroll-down-btn col-xs-3 col-xs-offset-5">
            <button type="button" goto="#whats">Scroll down for more!</button>
        </div>
    </div>
    <div id="whats" class="col-lg-12 page-para">

        <div id="1upload" class="etapes col-xs-2 col-md-2 col-xs-offset-1 "><img class="etp img-responsive"
                                                                                 src="/resources/img/upload.svg"
                                                                                 height="250"></div>
        <div id="2convert" class="etapes col-xs-2 col-md-2 col-xs-offset-1"><img class="etp img-responsive"
                                                                                 src="/resources/img/transform.svg"
                                                                                 height="250"></div>
        <div id="3paye" class="etapes col-xs-2 col-md-2 col-xs-offset-1"><img class="etp img-responsive"
                                                                              src="/resources/img/paye.svg"
                                                                              height="250"></div>
        <div id="4download" class="etapes col-xs-2 col-md-2 col-xs-offset-1"><img class="etp img-responsive"
                                                                                  src="/resources/img/download.svg"
                                                                                  height="250"></div>
        <div id="explicatif" class="col-xs-9 col-xs-offset-2"><h1><span class="small">Selectionnez un module ci-dessus pour plus de détails!</span>
        </h1></div>
    </div>
    <div id="whos" class="col-lg-12 col-md-12 page-para ">
        <div id="lunettes"><img src="/resources/img/lunettes.svg" height="100"></div>
        <img id="pablo" class="membre col-lg-1 col-lg-offset-1" style="background-image:url(/resources/img/image1.png)">
        <img id="sofiane" class="membre col-lg-1 col-lg-offset-1"
             style="background-image:url(/resources/img/image1.png)">
        <img id="michael" class="membre col-lg-1 col-lg-offset-1"
             style="background-image:url(/resources/img/image1.png)">
        <img id="lucas" class="membre col-lg-1 col-lg-offset-1" style="background-image:url(/resources/img/lucas.jpg)">
        <img id="thomas" class="membre col-lg-1 col-lg-offset-1" style="background-image:url(/resources/imgimage1.png)">

        <div id="description" class="col-lg-7 col-lg-offset-2"></div>

    </div>
    <div id="contact" class="col-lg-12 col-md-12 page-para ">
        <div id="coord" class="col-lg-4 col-md-offset-4 col-lg-offset-2"></div>
      <textarea id="txt" rows="10" cols="50" id="msg" class="col-lg-4 col-lg-offset-4" type="text"
                value="Indiquez nous toute info">

At w3schools.com you will learn how to make a website. We offer free tutorials in all web development technologies.
Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
</textarea>
        <input id="contactbutton" class="col-lg-3 col-lg-offset-4 btn btn-default" type="submit"
               value="Envoyer au sevice comm ou support">
    </div>

</div>



