<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="./css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <title>Login Inspectoria</title>

    <style>
        
        body {
            background-color: #f5f5f5;
        }
        .login-wrapper {
            min-height: 100vh;             
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-card {
            background: #fff;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 0 18px rgba(0,0,0,0.1);
            max-width: 420px;
            width: 100%;
        }
        .login-title {
            text-align: center;
            margin-bottom: 25px;
        }
        .icon-label {
            font-size: 18px;
            margin-right: 5px;
        }
        .demo-text {
            font-size: 12px;
            color: #777;
            margin-top: 10px;
        }
    </style>
</head>

<body>

<div class="login-wrapper">
    <div class="login-card">

       
        <script type="text/javascript">
            <% String mensaje = (String) request.getAttribute("alertaMensaje");
               if (mensaje != null && !mensaje.isEmpty()) { %>
                alert('<%= mensaje %>');
            <% } %>
        </script>

      
        <h1 class="login-title">Login</h1>

       
        <form action="ControladorLogin"
              method="POST"
              autocomplete="off">

            
            <div class="form-group">
                <label>
                    <span class="icon-label">👤</span> Usuario
                </label>
                <input class="form-control"
                       type="text"
                       name="usuarioLogin"
                       autocomplete="off"
                       autocorrect="off"
                       autocapitalize="off"
                       spellcheck="false"
                       required>
            </div>

           
            <div class="form-group">
                <label>
                    <span class="icon-label">🔒</span> Contraseña
                </label>
                <input class="form-control"
                       type="password"
                       name="passwordLogin"
                       autocomplete="off"
                       autocorrect="off"
                       autocapitalize="off"
                       spellcheck="false"
                       required>
            </div>

            
            <div class="form-group" style="margin-top: 15px;">
                <input class="btn btn-primary btn-block"
                       type="submit"
                       name="accion"
                       value="Verificar">
            </div>


</div>

</body>
</html>
