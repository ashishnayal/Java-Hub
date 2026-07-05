import Link from "next/link";

export default function Footer() {
  return (
    <footer className="footer" id="footer">
      <div className="container">
        <div className="footer-content">
          <div className="footer-brand">
            <h3>
              <span className="nav-logo-icon" style={{ width: 28, height: 28, fontSize: "0.9rem" }}>☕</span>
              Java Hub
            </h3>
            <p>
              The ultimate Java learning and interview preparation platform.
              Master every concept from JDK 1.0 to Java 26 with interactive
              code examples and comprehensive guides.
            </p>
          </div>

          <div className="footer-column">
            <h4>Explore</h4>
            <Link href="/timeline">Java Timeline</Link>
            <Link href="/versions">All Versions</Link>
            <Link href="/topics">Core Topics</Link>
            <Link href="/frameworks">Frameworks</Link>
          </div>

          <div className="footer-column">
            <h4>Learn</h4>
            <Link href="/topics/oop">OOP Concepts</Link>
            <Link href="/topics/collections">Collections</Link>
            <Link href="/topics/streams">Stream API</Link>
            <Link href="/topics/concurrency">Concurrency</Link>
          </div>

          <div className="footer-column">
            <h4>Prepare</h4>
            <Link href="/interview">Interview Corner</Link>
            <Link href="/topics/design-patterns">Design Patterns</Link>
            <Link href="/topics/jvm-internals">JVM Internals</Link>
            <Link href="/topics/modern-java">Modern Java</Link>
          </div>
        </div>

        <div className="footer-bottom">
          <span>© 2026 Java Hub. Built for Java developers, by Java developers.</span>
          <div className="footer-tech">
            <span className="footer-tech-badge">Next.js</span>
            <span className="footer-tech-badge">Spring Boot</span>
            <span className="footer-tech-badge">MongoDB</span>
          </div>
        </div>
      </div>
    </footer>
  );
}
