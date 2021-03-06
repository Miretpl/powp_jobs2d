package edu.kis.powp.jobs2d;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.adapter.DrawDriverAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.powp.jobs2d.events.FigureType;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.line.ExtendedLineFactory;
import edu.kis.powp.jobs2d.line.ModifiableLine;

public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		application.addTest("Figure Joe 1", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), FigureType.FIGURE_JOE_1));
		application.addTest("Figure Joe 2", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), FigureType.FIGURE_JOE_2));
		application.addTest("Figure Jane", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), FigureType.FIGURE_JANE));
		application.addTest("Figure Command", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), FigureType.FIGURE_COMMAND));
		application.addTest("Figure Complex Command", new SelectTestFigureOptionListener(DriverFeature.getDriverManager(), FigureType.FIGURE_COMPLEX_COMMAND));
	}

	/**
	 * Setup driver manager, and set default driver for application.
	 * 
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		Job2dDriver loggerDriver = new LoggerDriver();

		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

		Job2dDriver testDriver = new DrawDriverAdapter(DrawerFeature.getDrawerController());
		DriverFeature.addDriver("Draw Simulator", testDriver);

		Job2dDriver testDriver2 = new LineDrawerAdapter(DrawerFeature.getDrawerController(), ExtendedLineFactory.getBasicLine());
		DriverFeature.addDriver("Basic Line Simulator", testDriver2);

		Job2dDriver testDriver3 = new LineDrawerAdapter(DrawerFeature.getDrawerController(), ExtendedLineFactory.getDottedLine());
		DriverFeature.addDriver("Dotted Line Simulator", testDriver3);

		Job2dDriver testDriver4 = new LineDrawerAdapter(DrawerFeature.getDrawerController(), ExtendedLineFactory.getSpecialLine());
		DriverFeature.addDriver("Special Line Simulator", testDriver4);

		Job2dDriver testDriver5 = new LineDrawerAdapter(DrawerFeature.getDrawerController(), ExtendedLineFactory.getModifiableLine(Color.BLUE, 4.0f, true));
		DriverFeature.addDriver("Modifiable Line 1 Simulator", testDriver5);

		Job2dDriver testDriver6 = new LineDrawerAdapter(DrawerFeature.getDrawerController(), ExtendedLineFactory.getModifiableLine(Color.GREEN, 10.0f, false));
		DriverFeature.addDriver("Modifiable Line 2 Simulator", testDriver6);

		DriverFeature.updateDriverInfo();
	}

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("2d jobs Visio");
				DrawerFeature.setupDrawerPlugin(app);

				DriverFeature.setupDriverPlugin(app);
				setupDrivers(app);
				setupPresetTests(app);
				setupLogger(app);

				app.setVisibility(true);
			}
		});
	}

}
