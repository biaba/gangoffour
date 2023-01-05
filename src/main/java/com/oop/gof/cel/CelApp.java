package com.oop.gof.cel;

import com.google.api.expr.v1alpha1.Decl;
import com.google.protobuf.Descriptors;
import org.projectnessie.cel.Env;
import org.projectnessie.cel.EnvOption;
import org.projectnessie.cel.Program;
import org.projectnessie.cel.checker.Decls;
import org.projectnessie.cel.common.types.pb.ProtoTypeRegistry;
import org.projectnessie.cel.common.types.ref.Val;
import org.projectnessie.cel.tools.Script;
import org.projectnessie.cel.tools.ScriptException;
import org.projectnessie.cel.tools.ScriptHost;

import java.util.*;

public class CelApp {

    public static void main(String[] a) throws ScriptException {
        simpleCel();
        simpleCel2("display_name == 'juris'");
    }

    public static void simpleCel2(String filter) {
        Account variable1 = Account.newBuilder()
                .setPhoneNumber("224433")
                .setDisplayName("accountDisplayName")
                .setUserId("0001").build();

        Descriptors.Descriptor accountDescriptor = Account.getDescriptor();
        List<Decl> declsList = new ArrayList<>();

        declsList.add(Decls.newVar(accountDescriptor.findFieldByName("user_id").getName(), Decls.Dyn));
        declsList.add(Decls.newVar(accountDescriptor.findFieldByName("display_name").getName(), Decls.String));
        declsList.add(Decls.newVar(accountDescriptor.findFieldByName("phone_number").getName(), Decls.String));
        declsList.add(Decls.newVar(accountDescriptor.findFieldByName("emails").getName(), Decls.newListType(Decls.String)));

        ProtoTypeRegistry celTypeRegistry = ProtoTypeRegistry.newRegistry();
        celTypeRegistry.registerMessage(Account.getDefaultInstance());
        Env env = Env.newEnv(
                EnvOption.container(accountDescriptor.getFullName()),
                EnvOption.customTypeProvider(celTypeRegistry),
                EnvOption.customTypeAdapter(celTypeRegistry),
                EnvOption.declarations(declsList));

// compiling user requested filter against environment
        Env.AstIssuesTuple astAndIssues = env.compile(filter);

        if(astAndIssues.hasIssues()) {
            throw new RuntimeException("CEL in wrong format: "+ astAndIssues.getIssues());
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


        System.out.println("Result of CEL evaluation - does the entry exist?: "+ result.booleanValue());

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
