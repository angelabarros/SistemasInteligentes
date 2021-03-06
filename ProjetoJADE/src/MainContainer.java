import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;


public class MainContainer {

	Runtime rt;
	ContainerController container;
	
	
	//fazer a definição do mapa
	int[][] mapa = new int[100][100]; //passar esta info para os agentes
	
	//definir zonas do mapa (zonas residenciais, comerciais, etc)
	
	//definir pontos de abastecimento
	
	
	public static void main(String[] args) {
		MainContainer a = new MainContainer();

		a.initMainContainerInPlatform("localhost", "9889", "MainContainer");
		
		
		//registar agentes participativos
		a.startAgentInPlatform("Incendiario", "agents.Incendiario");
		a.startAgentInPlatform("Quartel", "agents.Quartel");
		//a.startAgentInPlatform("Drone0000", "agents.Drone");
		a.startAgentInPlatform("Drone0001", "agents.Drone");
		a.startAgentInPlatform("Drone0002", "agents.Drone");
		a.startAgentInPlatform("Drone0003", "agents.Drone");
		a.startAgentInPlatform("Drone0004", "agents.Drone");
		a.startAgentInPlatform("Drone0005", "agents.Drone");
		a.startAgentInPlatform("Drone0006", "agents.Drone");
		a.startAgentInPlatform("Drone0007", "agents.Drone");
		a.startAgentInPlatform("Drone0008", "agents.Drone");
		a.startAgentInPlatform("Drone0009", "agents.Drone");
		a.startAgentInPlatform("Drone0010", "agents.Drone");
		
		
//		a.startAgentInPlatform("Camiao000", "agents.camiao");
		a.startAgentInPlatform("Camiao001", "agents.camiao");
		a.startAgentInPlatform("Camiao002", "agents.camiao");
		a.startAgentInPlatform("Camiao003", "agents.camiao");
		a.startAgentInPlatform("Camiao004", "agents.camiao");
		a.startAgentInPlatform("Camiao005", "agents.camiao");
		
		
		a.startAgentInPlatform("Aeronave1", "agents.aeronave");
		a.startAgentInPlatform("Aeronave2", "agents.aeronave");
		a.startAgentInPlatform("Interface", "agents.Interface");
		
		

	
	}

	public ContainerController initContainerInPlatform(String host, String port, String containerName) {
		// Get the JADE runtime interface (singleton)
		this.rt = Runtime.instance();

		// Create a Profile, where the launch arguments are stored
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.CONTAINER_NAME, containerName);
		profile.setParameter(Profile.MAIN_HOST, host);
		profile.setParameter(Profile.MAIN_PORT, port);
		// create a non-main agent container
		ContainerController container = rt.createAgentContainer(profile);
		return container;
	}

	public void initMainContainerInPlatform(String host, String port, String containerName) {

		// Get the JADE runtime interface (singleton)
		this.rt = Runtime.instance();

		// Create a Profile, where the launch arguments are stored
		Profile prof = new ProfileImpl();
		prof.setParameter(Profile.CONTAINER_NAME, containerName);
		prof.setParameter(Profile.MAIN_HOST, host);
		prof.setParameter(Profile.MAIN_PORT, port);
		prof.setParameter(Profile.MAIN, "true");
		prof.setParameter(Profile.GUI, "true");

		// create a main agent container
		this.container = rt.createMainContainer(prof);
		rt.setCloseVM(true);

	}

	public void startAgentInPlatform(String name, String classpath) {
		try {
			AgentController ac = container.createNewAgent(name, classpath, new Object[0]);
			ac.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}