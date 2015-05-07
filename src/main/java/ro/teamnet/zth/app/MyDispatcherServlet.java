package ro.teamnet.zth.app;

import org.codehaus.jackson.map.ObjectMapper;
import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.api.annotations.MyRequestParam;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;
import ro.teamnet.zth.fmk.ParameterAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Adina Radu on 5/6/2015.
 */

public class MyDispatcherServlet extends HttpServlet {

    HashMap<String, MethodAttributes> allowedMethods = new HashMap<>();

    private HashMap<String, MethodAttributes> getAllowedRequestMethods(Iterable<Class> classes) {
        for (Class controller : classes)
            if (controller.isAnnotationPresent(MyController.class)) {
                MyController myCtrlAnnotation = (MyController) controller.getAnnotation(MyController.class);
                String controllerUrlPath = myCtrlAnnotation.urlPath();
                Method[] methods = controller.getMethods();
                for (Method controllerMethod : methods) {
                    if (controllerMethod.isAnnotationPresent(MyRequestMethod.class)) {
                        MyRequestMethod methodAnnotation = (MyRequestMethod) controllerMethod.getAnnotation(MyRequestMethod.class);
                        String key = controllerUrlPath + methodAnnotation.urlPath();


                        MethodAttributes methodAttributes = new MethodAttributes();

                        methodAttributes.setControllerClass(controller.getName());
                        methodAttributes.setMethodName(controllerMethod.getName());
                        methodAttributes.setMethodType(methodAnnotation.methodType());
                        methodAttributes.setMethodParams(new ArrayList<ParameterAttributes>());
                        Annotation[][] parameterAnnotations = controllerMethod.getParameterAnnotations();
                        for (Annotation[] parameterAnnotation : parameterAnnotations) {
                            for (Annotation annotation : parameterAnnotation) {
                                if(annotation instanceof MyRequestParam){
                                    ParameterAttributes e = new ParameterAttributes();
                                    e.setName(((MyRequestParam)annotation).name());
                                    e.setType(((MyRequestParam)annotation).type());
                                    methodAttributes.getMethodParams().add(e);
                                }
                            }
                        }
                        allowedMethods.put(key, methodAttributes);
                    }
                }
            }
        return allowedMethods;
    }

    @Override
    public void init() throws ServletException {
        try {
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.teamnet.zth.app.controller");
            getAllowedRequestMethods(classes);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            dispatchReply("GET", req, resp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            dispatchReply("GET", req, resp);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void dispatchReply(String method, HttpServletRequest req, HttpServletResponse resp) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        try {
            Object r = dispatch(req, resp);
            reply(r, req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DispathcException de) {
            sendException(de, resp);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private void sendException(DispathcException de, HttpServletResponse resp) {
        PrintWriter out = null;
        try {
            out = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Nu s-a mapat URL-ul");
    }


    private Object dispatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String path = req.getPathInfo();

        MethodAttributes methodAttributes = allowedMethods.get(path);

        if (methodAttributes != null) {
            /*Apel Application Controller*/

            Class<?> controller = Class.forName(methodAttributes.getControllerClass());
            Object object = controller.newInstance();
            List<Class> paramTypes = new ArrayList<>();
            List<Object> paramValues = new ArrayList<>();
            for (ParameterAttributes parameterAttributes : methodAttributes.getMethodParams()) {
                Class type = parameterAttributes.getType();
                paramTypes.add(type);
                String stringValue = req.getParameter(parameterAttributes.getName());
               // Object value = type.cast(stringValue);
                paramValues.add(stringValue);
            }
            Method method = controller.getMethod(methodAttributes.getMethodName(),paramTypes.toArray(new Class[paramTypes.size()] ));
            controller.getMethods();
            Object result = method.invoke(object, paramValues.toArray(new Object [paramValues.size()]));
            return result;
        }

        throw new DispathcException();
    }



    private void reply(Object r, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        //ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        //String json = ow.writeValueAsString(r);


        String s = new ObjectMapper().writeValueAsString(r);
        PrintWriter out = resp.getWriter();
        out.print(s);
    }

}
