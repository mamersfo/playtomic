import static play.mvc.Results.internalServerError;
import play.GlobalSettings;
import play.libs.Json;
import play.mvc.Result;

public class Global extends GlobalSettings
{
    @Override
    public Result onError( play.mvc.Http.RequestHeader request, Throwable t )
    {
        return internalServerError(
            Json.toJson( new models.Error( t.getCause() ) )
        );
    }
}
