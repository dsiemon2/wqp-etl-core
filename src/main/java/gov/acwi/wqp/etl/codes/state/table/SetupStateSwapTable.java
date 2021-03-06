package gov.acwi.wqp.etl.codes.state.table;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SetupStateSwapTable {

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("dropStateSwapTable")
	private Tasklet dropStateSwapTable;

	@Autowired
	@Qualifier("createStateSwapTable")
	private Tasklet createStateSwapTable;

	@Bean
	public Step dropStateSwapTableStep() {
		return stepBuilderFactory.get("dropStateSwapTableStep")
				.tasklet(dropStateSwapTable)
				.build();
	}

	@Bean
	public Step createStateSwapTableStep() {
		return stepBuilderFactory.get("createStateSwapTableStep")
				.tasklet(createStateSwapTable)
				.build();
	}

	@Bean
	public Flow setupStateSwapTableFlow() {
		return new FlowBuilder<SimpleFlow>("setupStateSwapTableFlow")
				.start(dropStateSwapTableStep())
				.next(createStateSwapTableStep())
				.build();
	}
}
