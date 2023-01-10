package com.oop.gof.cel;

import com.google.api.expr.v1alpha1.Decl;
import com.google.protobuf.*;
import org.projectnessie.cel.Env;
import org.projectnessie.cel.EnvOption;
import org.projectnessie.cel.Program;
import org.projectnessie.cel.checker.Decls;
import org.projectnessie.cel.common.types.pb.ProtoTypeRegistry;
import org.projectnessie.cel.common.types.ref.Val;
import org.projectnessie.cel.tools.Script;
import org.projectnessie.cel.tools.ScriptException;
import org.projectnessie.cel.tools.ScriptHost;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.oop.gof.cel.Account.parseFrom;


public class CelApp {

    public static void main(String[] a) throws ScriptException, IOException {
        simpleCel();
        simpleCel2("display_name == 'juris'");
        simpleCel3("display_name == 'kristers'");

        extendedFieldOption();
    }

    public static void extendedFieldOption() {
        Account account = javaInstance();

        ExtensionRegistry registry = ExtensionRegistry.newInstance();
cd 
        registry.add(com.oop.gof.cel.Account.level);
        String valueOfExtension =
                Account.getDescriptor()
                        .findFieldByName("user_id")
                        .getOptions()
                .getExtension(com.oop.gof.cel.Account.level);
        System.out.println("Extension of field user_id: "+ valueOfExtension);
    }

    public static void simpleCel3(String filter) throws IOException {
        Env env = prepareEnv();

        Program program = compileAndGetProgram(env, filter);

// values from DB or other source
        Map<String, Object> variables2 = new HashMap<>();
        variables2.put("client_id", "123");
        variables2.put("display_name", "anna");
        variables2.put("phone_number", "321");
        variables2.put("emails", Collections.emptyList());

// evaluating user requested filter against existing value
        Message accountValue = messageFromJavaInstance();
        byte[] accountByteArray = accountValue.toByteArray();
        InputStream targetStream = new ByteArrayInputStream(accountByteArray);
        Account account = parseFrom(targetStream);
        Map<String, Object> accountMap = new HashMap<>();
        accountMap.put("display_name", accountValue.getField(Account.getDescriptor().findFieldByName("display_name")));
        Val result = program.eval(accountMap).getVal();
        System.out.println("Auto convert - java instance-> Message -> feeding in CEL. \n " +
                "Result of CEL evaluation - does the entry exist?: " + result.booleanValue());
        accountMap.put("display_name", account.getDisplayName());
        result = program.eval(accountMap).getVal();

        System.out.println("Auto convert - java instance-> Message -> feeding in CEL. \n " +
                "Result of CEL evaluation - does the entry exist?: " + result.booleanValue());

    }

    public static Message messageFromJavaInstance() {
        return Account.newBuilder()
                .setPhoneNumber("887766")
                .setDisplayName("messageAccount")
                .setUserId("555")
                .addEmails("anna@anna.lv").build();
    }

    public static Account javaInstance() {
        return Account.newBuilder()
                .setPhoneNumber("887766")
                .setDisplayName("messageAccount")
                .setUserId("555")
                .addEmails("anna@anna.lv").build();
    }

    public static Program compileAndGetProgram(Env env, String filter) {
        // compiling user requested filter against environment
        Env.AstIssuesTuple astAndIssues = env.compile(filter);

        if (astAndIssues.hasIssues()) {
            throw new RuntimeException("CEL in wrong format: " + astAndIssues.getIssues());
        }
// getting program if compiled value without errors
        return env.program(astAndIssues.getAst());
    }

    public static void simpleCel2(String filter) {
        Account variable1 = Account.newBuilder()
                .setPhoneNumber("224433")
                .setDisplayName("accountDisplayName")
                .setUserId("0001").build();


        Env env = prepareEnv();

// compiling user requested filter against environment
        Env.AstIssuesTuple astAndIssues = env.compile(filter);

        if (astAndIssues.hasIssues()) {
            throw new RuntimeException("CEL in wrong format: " + astAndIssues.getIssues());
        }
// getting program if compiled value without errors
        Program program = env.program(astAndIssues.getAst());

// values from DB or other source
        Map<String, Object> variables2 = new HashMap<>();
        variables2.put("client_id", "123");
        variables2.put("display_name", "anna");
        variables2.put("phone_number", "321");
        variables2.put("emails", Collections.emptyList());

// evaluating user requested filter against existing value
        Val result = program.eval(variables2).getVal();


        System.out.println("Result of CEL evaluation - does the entry exist?: " + result.booleanValue());

/*
        // Compile CEL Program.
        val eventGroupDescriptor = EventGroup.getDescriptor()
        val env =
                Env.newEnv(
                        EnvOption.container(eventGroupDescriptor.fullName),
                        EnvOption.customTypeProvider(celTypeRegistry),
                        EnvOption.customTypeAdapter(celTypeRegistry),
                        EnvOption.declarations(
                                eventGroupDescriptor.fields
                                        .map {
            Decls.newVar(
                    it.name,
                    celTypeRegistry.findFieldType(eventGroupDescriptor.fullName, it.name).type
            )
        }
        // TODO(projectnessie/cel-java#295): Remove when fixed.
            .plus(Decls.newVar(METADATA_FIELD, Checked.checkedAny))
        )
      )
        val astAndIssues = env.compile(filter)
        if (astAndIssues.hasIssues()) {
            throw Status.INVALID_ARGUMENT.withDescription(
                            "filter is not a valid CEL expression: ${astAndIssues.issues}"
                    )
                    .asRuntimeException()
        }
        val program = env.program(astAndIssues.ast)

        return eventGroups.filter { eventGroup ->
                val variables: Map<String, Any> =
            mutableMapOf<String, Any>().apply {
                for ((fieldDescriptor, value) in eventGroup.allFields) {
                    put(fieldDescriptor.name, value)
                }
                // TODO(projectnessie/cel-java#295): Remove when fixed.
                if (eventGroup.hasMetadata()) {
                    val metadata: com.google.protobuf.Any = eventGroup.metadata.metadata
                    put(
                            METADATA_FIELD,
                            DynamicMessage.parseFrom(
                                    typeRegistry.getDescriptorForTypeUrl(metadata.typeUrl),
                                    metadata.value
                            )
                    )
                }
            }
            val result: Val = program.eval(variables).`val`
            if (result is Err) {
                throw result.toRuntimeException()
            }
            result.booleanValue()
        }
    }
    */
    }

    public static Env prepareEnv() {
        Descriptors.Descriptor accountDescriptor = Account.getDescriptor();
        List<Decl> declsList = new ArrayList<>();

        declsList.add(Decls.newVar(accountDescriptor.findFieldByName("user_id").getName(), Decls.String));
        declsList.add(Decls.newVar(accountDescriptor.findFieldByName("display_name").getName(), Decls.String));
        declsList.add(Decls.newVar(accountDescriptor.findFieldByName("phone_number").getName(), Decls.String));
        declsList.add(Decls.newVar(accountDescriptor.findFieldByName("emails").getName(), Decls.newListType(Decls.String)));

        ProtoTypeRegistry celTypeRegistry = ProtoTypeRegistry.newRegistry();
        celTypeRegistry.registerMessage(Account.getDefaultInstance());
        return Env.newEnv(
                EnvOption.container(accountDescriptor.getFullName()),
                EnvOption.customTypeProvider(celTypeRegistry),
                EnvOption.customTypeAdapter(celTypeRegistry),
                EnvOption.declarations(declsList));

    }

    public static void simpleCel() throws ScriptException {
        // Build the script factory
        ScriptHost scriptHost = ScriptHost.newBuilder().build();

        // create the script, will be parsed and checked
        Script script = scriptHost.buildScript("x + ' ' + y")
                .withDeclarations(
                        // Variable declarations - we need `x` and `y` in this example
                        Decls.newVar("x", Decls.String),
                        Decls.newVar("y", Decls.String))
                .build();

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x", "hello");
        arguments.put("y", "world");

        String result = script.execute(String.class, arguments);

        System.out.println(result); // Prints "hello world"
    }
}
