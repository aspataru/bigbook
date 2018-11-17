package streams;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestOptional {

	private final Database<Person> db = Database.initialize(new Person("Alice"));

	@Test
	public void optionalOrElseWillEvaluateTheElseAlways() {
		List<Person> inserted = Stream.of("Alice", "Bob", "David", "Pierre")
				.map(s -> db.getByIdentifier(s).orElse(db.insert(new Person(s))))
				.collect(Collectors.toList());

		List<Person> actuallyInDb = db.getAll();

		Assertions.assertThat(actuallyInDb).hasSize(inserted.size() + 1);
	}

	@Test
	public void optionalOrElseGetWillNotEvaluateTheElseUnlessNeeded() {
		List<Person> inserted = Stream.of("Alice", "Bob", "David", "Pierre")
				.map(s -> db.getByIdentifier(s).orElseGet(() -> db.insert(new Person(s))))
				.collect(Collectors.toList());

		List<Person> actuallyInDb = db.getAll();

		Assertions.assertThat(actuallyInDb).hasSameSizeAs(inserted);
	}


	public interface WithId {
		String getId();
	}

	@RequiredArgsConstructor
	@ToString
	class Person implements WithId {
		private final String name;

		@Override
		public String getId() {
			return this.name;
		}
	}

	static class Database<DTO extends WithId> {

		private final List<DTO> storage;

		private Database() {
			storage = new LinkedList<>();
		}

		static <DTO extends WithId> Database<DTO> initialize(DTO dto) {
			Database<DTO> db = new Database<>();
			db.insert(dto);
			return db;
		}

		DTO insert(DTO identifier) {
			storage.add(identifier);
			return identifier;
		}

		Optional<DTO> getByIdentifier(String identifier) {
			return storage.stream()
					.filter(dto -> dto.getId().equals(identifier))
					.findFirst();
		}

		List<DTO> getAll() {
			return storage;
		}
	}

}
